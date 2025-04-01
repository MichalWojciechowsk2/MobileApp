package pl.wsei.pam.lab02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pl.wsei.pam.lab01.R
import pl.wsei.pam.lab03.Lab03Activity

class Lab02Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lab02)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.favorites_grid)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button1 = findViewById<View>(R.id.button1)
        val button2 = findViewById<View>(R.id.button2)
        val button3 = findViewById<View>(R.id.button3)
        val button4 = findViewById<View>(R.id.button4)
        val buttons = listOf(button1,button2,button3,button4)
        buttons.forEach{button ->
            button.setOnClickListener{v -> setGameMap(v)}
        }
    }
    private fun setGameMap(v: View){
        val tag: String? = v.tag as String?
        val tokens: List<String>? = tag?.split(" ")
        val rows = tokens?.get(0)?.toInt()
        val columns = tokens?.get(1)?.toInt()

        if(rows != null && columns != null){
            val intent = Intent(this, Lab03Activity::class.java)
            val size: IntArray = intArrayOf(rows, columns)
            intent.putExtra("size", size)
            startActivity(intent)
            Log.d("Lab02Activity", "Starting Lab03Activity with rows: $rows, columns: $columns")
            Toast.makeText(this, "rows: ${rows}, columns ${columns}", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Error while reading board size!", Toast.LENGTH_SHORT).show()
        }




    }
}