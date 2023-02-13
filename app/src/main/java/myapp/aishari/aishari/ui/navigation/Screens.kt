package myapp.aishari.aishari.ui.navigation

sealed class Screens(val route: String) {
    object PalindromeScreen: Screens("palindrome")
    object UnitConverterScreen: Screens("unitconverter")
    object QuizScreen: Screens("quiz")
}