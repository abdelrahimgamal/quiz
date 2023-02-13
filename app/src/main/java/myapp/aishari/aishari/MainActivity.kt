package myapp.aishari.aishari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import myapp.aishari.aishari.ui.navigation.Screens
import myapp.aishari.aishari.ui.palindrome.PalindromeScreen
import myapp.aishari.aishari.ui.quiz.QuizScreen
import myapp.aishari.aishari.ui.theme.Aishari_oblig1Theme
import myapp.aishari.aishari.ui.unitconverter.UnitConverterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aishari_oblig1Theme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.PalindromeScreen.route
    )
    {
        composable(route = Screens.PalindromeScreen.route) {
            PalindromeScreen(
                (navController)
            )
        }
        composable(route = Screens.UnitConverterScreen.route) {
            UnitConverterScreen(navController)
        }
        composable(route = Screens.QuizScreen.route) {
            QuizScreen(navController)
        }
    }
}