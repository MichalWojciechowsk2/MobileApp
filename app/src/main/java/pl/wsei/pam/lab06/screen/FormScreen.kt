import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.wsei.pam.lab06.data.AppViewModelProvider
import pl.wsei.pam.lab06.data.FormViewModel
import pl.wsei.pam.lab06.ui.components.AppTopBar
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    navController: NavController,
    viewModel: FormViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                title = "Form",
                showBackIcon = true,
                route = "form",
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.save()
                        navController.navigate("list") {
                            popUpTo("list") { inclusive = true }
                        }
                    }
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onHomeClick = {
                    navController.navigate("home")
                }
            )
        }
    ) {
        TodoTaskInputBody(
            todoUiState = viewModel.todoTaskUiState,
            onItemValueChange = viewModel::updateUiState,
            modifier = Modifier.padding(it)
        )
    }
}

