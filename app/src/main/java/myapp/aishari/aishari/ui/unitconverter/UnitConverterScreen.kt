package myapp.aishari.aishari.ui.unitconverter

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import myapp.aishari.aishari.R
import myapp.aishari.aishari.ui.navigation.Screens

@Composable
fun UnitConverterScreen(
    navController: NavController,

    ) {
    myApp(navController)

}

fun String.isNumeric(): Boolean {
    return this.all { char -> char.isDigit() }
}

@Composable
fun goToNextPage(navController: NavController) {

    Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
        Button(
            onClick = {
                navController.navigate(Screens.QuizScreen.route)
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Go To Quiz Screen")
        }
    }
}


fun Double.roundTheNumber(): String {

    return "%.2f".format(this)

}


private fun getNumber(_s: Double, selectedText: String): Double {
    return when (selectedText) {
        "Ounce" -> _s * 33.814
        "Cup" -> _s * 4.22
        "Hogshead" -> _s * 0.0041
        "Gallon" -> _s * 0.264
        else -> {
            0.0
        }
    }

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
                    Text(text = "UnitConverter Screen")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
                    }
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
                    var litres = remember { mutableStateOf(TextFieldValue()) }
                    var text by remember { mutableStateOf("Please Select the Quantity and Unit") }
                    var expanded by remember { mutableStateOf(false) }
                    val units = stringArrayResource(R.array.converters)
                    var selectedText by remember { mutableStateOf("") }
                    val context = LocalContext.current
                    var dropDownWidth by remember { mutableStateOf(0) }


                    Spacer(modifier = Modifier.fillMaxWidth())

                    TextField(
                        label = { Text(text = "Amount of Litres") },
                        value = litres.value, modifier = Modifier.focusRequester(focusRequester),
                        onValueChange = { litres.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )





                    Column(Modifier.padding(40.dp, 0.dp)) {
                        OutlinedTextField(
                            value = selectedText,
                            onValueChange = { selectedText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onSizeChanged {
                                    dropDownWidth = it.width
                                },
                            label = { Text("Unit") },
                            trailingIcon = {
                                Icon(
                                    Icons.Filled.ArrowDropDown,
                                    "contentDescription",
                                    Modifier.clickable { expanded = !expanded })
                            }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { dropDownWidth.toDp() })
                        ) {
                            units.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    selectedText = label
                                    expanded = false
                                }, text = { Text(text = label) })
                            }
                        }
                    }
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {

                                if (litres.value.text.isEmpty()) {
                                    focusRequester.requestFocus()
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Please Enter The Amount Of Litres"
                                        )
                                    }
                                    return@Button
                                } else if (selectedText.isEmpty()) {

                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Please Select The Unit To Convert To"
                                        )
                                    }
                                    return@Button
                                } else if (!litres.value.text.isNumeric()) {
                                    focusRequester.requestFocus()
                                    litres.value = litres.value.copy("")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Please Pick Numbers Only"
                                        )
                                    }
                                    return@Button
                                }

                                text = getNumber(
                                    litres.value.text.toDouble(),
                                    selectedText
                                ).roundTheNumber() + " " + selectedText
                                focusManager.clearFocus()

                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Calculate")
                        }
                    }
                    Text(text = text)
                    goToNextPage(navController)

                }
            }
        }
    )
}





