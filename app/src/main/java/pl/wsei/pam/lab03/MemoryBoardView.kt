package pl.wsei.pam.lab03

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import pl.wsei.pam.lab01.R
import java.util.Random
import java.util.Stack

class MemoryBoardView(
    private val gridLayout: androidx.gridlayout.widget.GridLayout,
    private val cols: Int,
    private val rows: Int
) {
    private val tiles: MutableMap<String, Tile> = mutableMapOf()
    private val icons: List<Int> = listOf(
        R.drawable.baseline_1x_mobiledata_24,
        R.drawable.baseline_360_24,
        R.drawable.baseline_access_alarms_24,
        R.drawable.baseline_accessibility_new_24,
        R.drawable.baseline_adb_24,
        R.drawable.baseline_air_24,
        R.drawable.baseline_airplay_24,
        R.drawable.baseline_garage_24,
        R.drawable.baseline_headset_24,
        R.drawable.baseline_hive_24,
        R.drawable.baseline_hotel_24,
        R.drawable.baseline_iron_24,
        R.drawable.baseline_key_24,
        R.drawable.baseline_man_24,
        R.drawable.baseline_nightlight_24,
        R.drawable.baseline_notifications_24,
        R.drawable.baseline_roller_skating_24,
        R.drawable.baseline_sailing_24,
    )
    private val deckResource: Int = R.drawable.baseline_blur_on_24
    private var onGameChangeStateListener: (MemoryGameEvent) -> Unit = { (e) -> }
    private val matchedPair: Stack<Tile> = Stack()
    private val logic: MemoryGameLogic = MemoryGameLogic(cols * rows / 2)

    init {
        val shuffledIcons: MutableList<Int> = mutableListOf<Int>().also {
            it.addAll(icons.subList(0, cols * rows / 2))
            it.addAll(icons.subList(0, cols * rows / 2))
            it.shuffle()
        }

        var index = 0
        for(row in 0 until rows) {
            for(col in 0 until cols){

                val btn = ImageButton(gridLayout.context).also {
                    it.tag = "$row $col"
                    val layoutParams = androidx.gridlayout.widget.GridLayout.LayoutParams()
                    it.setImageResource(R.drawable.baseline_blur_on_24)
                    it.scaleType = ImageView.ScaleType.FIT_CENTER
                    layoutParams.width = 0
                    layoutParams.height = 0
                    layoutParams.setMargins(5,5,5,5)
                    layoutParams.setGravity(Gravity.CENTER)
                    layoutParams.columnSpec = androidx.gridlayout.widget.GridLayout.spec(col, 1, 1f)
                    layoutParams.rowSpec = androidx.gridlayout.widget.GridLayout.spec(row, 1, 1f)
                    it.layoutParams = layoutParams
                    gridLayout.addView(it)
                }
                val tileResource = shuffledIcons.removeAt(0)
                val tile = Tile(btn, tileResource, deckResource)
                tiles[btn.tag.toString()] = tile
                btn.setOnClickListener(::onClickTile)
            }
        }
    }

    fun getState(): IntArray {
        return tiles.values.flatMap { tile ->
            listOf(if (tile.revealed) 1 else 0, tile.tileResource)
        }.toIntArray()
    }

    fun setState(state: IntArray) {
        var index = 0
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val tile = tiles["$row $col"]
                val isRevealed = state[index] == 1
                val resource = state[index + 1]
                if (tile != null) {
                    tile.tileResource = resource
                    tile.revealed = isRevealed
                    tile.button.setImageResource(if (isRevealed) resource else deckResource)
                }
                index += 2
            }
        }
    }

    private fun onClickTile(v: View) {
        val tile = tiles[v.tag]
        if(tile == null || tile.revealed) return
        matchedPair.push(tile)
        val matchResult = logic.process {
            tile?.tileResource?:-1
        }
        onGameChangeStateListener(MemoryGameEvent(matchedPair.toList(), matchResult))
        if (matchResult != GameStates.Matching) {
            matchedPair.clear()
        }
    }

    fun setOnGameChangeListener(listener: (event: MemoryGameEvent) -> Unit) {
        onGameChangeStateListener = listener
    }

    private fun addTile(button: ImageButton, resourceImage: Int) {
        button.setOnClickListener(::onClickTile)
        val tile = Tile(button, resourceImage, deckResource)
        tiles[button.tag.toString()] = tile
    }

     fun animatePairedButton(button: ImageButton, action: Runnable ) {
        val set = AnimatorSet()
        val random = Random()
        button.pivotX = random.nextFloat() * 200f
        button.pivotY = random.nextFloat() * 200f

        val rotation = ObjectAnimator.ofFloat(button, "rotation", 720f)
        val scallingX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 1.2f)
        val scallingY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 1.2f)
        val fade = ObjectAnimator.ofFloat(button, "alpha", 1f, 0.8f)
        set.startDelay = 200
        set.duration = 1000
        set.interpolator = DecelerateInterpolator()
        set.playTogether(rotation, scallingX, scallingY, fade)
        set.addListener(object: Animator.AnimatorListener {

            override fun onAnimationStart(animator: Animator) {
            }

            override fun onAnimationEnd(animator: Animator) {
                button.scaleX = 1f
                button.scaleY = 1f
                button.alpha = 1.0f
                action.run();
            }

            override fun onAnimationCancel(animator: Animator) {
            }

            override fun onAnimationRepeat(animator: Animator) {
            }
        })
        set.start()
    }

    fun animateWrongPairedButton(button: ImageButton, action: Runnable ) {
        val set = AnimatorSet()
        val random = Random()
        button.pivotX = random.nextFloat() * 200f

        val rotationToRight = ObjectAnimator.ofFloat(button, "rotation", 4f)
        val rotationToLeft = ObjectAnimator.ofFloat(button, "rotation", -4f)
        val rotationToZero = ObjectAnimator.ofFloat(button, "rotation", 4f)
        set.startDelay = 200
        set.duration = 40
        set.interpolator = DecelerateInterpolator()
        set.playSequentially(rotationToRight,rotationToLeft, rotationToZero)
        set.addListener(object: Animator.AnimatorListener {

            override fun onAnimationStart(animator: Animator) {
            }

            override fun onAnimationEnd(animator: Animator) {
                button.rotation = 0f
                action.run();
            }

            override fun onAnimationCancel(animator: Animator) {
            }

            override fun onAnimationRepeat(animator: Animator) {
            }
        })
        set.start()
    }

}