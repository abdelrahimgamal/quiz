package myapp.aishari.aishari.ui.palindrome

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import myapp.aishari.aishari.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PalindromeScreen(
    navController: NavController
) {
    myApp(navController = navController)
}

@Composable
fun GoToConverterScreen(navController: NavController) {
    Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
        Button(
            onClick = {
                navController.navigate(Screens.UnitConverterScreen.route)
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Go To Converter Screen")
        }
    }

}


private fun check(_s: String): Boolean {
    //mutate string, removing leading and trailing garbage
    var mutant = _s.replace("^[^a-zA-Z0-9]+".toRegex(), "")
        .replace("[^a-zA-Z0-9]+$".toRegex(), "")

    if (mutant.length <= 1) return true    // base case

    var matches = mutant.first().toLowerCase() == mutant.last().toLowerCase()
    return if (matches)
    // clip first and last chars, call recursively
        check(mutant.substring(1, mutant.lastIndex))
    else
        false // call again or fail
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun myApp(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Palindrome Screen")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )
        },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val focusRequester = remember { FocusRequester() }
                    val focusManager = LocalFocusManager.current
                    val myWord = remember { mutableStateOf(TextFieldValue()) }
                    var text by remember { mutableStateOf("") }
                    val context = LocalContext.current
                    Spacer(modifier = Modifier.fillMaxWidth())
                    Text(text = "Please Enter A Word")
                    TextField(
                        label = { Text(text = "Word") },
                        value = myWord.value, modifier = Modifier.focusRequester(focusRequester),
                        onValueChange = { myWord.value = it })

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (myWord.value.text.isEmpty()) {
                                    focusRequester.requestFocus()
                                    Toast.makeText(
                                        context,
                                        "Please Enter A Word",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button

                                }

                                if (!check(myWord.value.text)) {
                                    text = "${myWord.value.text} Is NOT a Palindrome"
                                } else {
                                    text = "${myWord.value.text} Is a Palindrome"
                                }
                                focusManager.clearFocus()
                                myWord.value = myWord.value.copy("")

                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Check")
                        }
                    }
                    Text(text = text)

                    GoToConverterScreen(navController)

                }
            }
        }
    )
}




