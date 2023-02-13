package myapp.aishari.aishari.ui.quiz

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import myapp.aishari.aishari.data.Question


@Composable
fun QuizScreen(
    navController: NavController
) {
    myApp(navController = navController)
}


private fun createList(): List<Question> {
    return listOf(
        Question("hot water freezes faster than cold water ?", true),
        Question("bees are blind?", false),
        Question("A shrimp heart is located in its head ?", true),
        Question("The Hundred Years' war ended lasted 99 years ?", false),
        Question("Eggs Contain Choline ?", true),
    )
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
                    Text(text = "Quiz Screen")
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
                val quizState = remember { mutableStateOf(0) }
                val scoreState = remember { mutableStateOf(0) }
                val enabled =
                    quizState.value < createList().size
                val context = LocalContext.current


                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.fillMaxWidth())
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        val text =
                            if (quizState.value < createList().size) createList()[quizState.value].question else "Game Over"
                        Text(
                            text = text,
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp, 0.dp, 40.dp, 0.dp)
                    ) {
                        Button(onClick = {
                            if (quizState.value < createList().size) {
                                if (createList()[quizState.value].answer) {
                                    scoreState.value = scoreState.value + 1
                                }
                                quizState.value = quizState.value + 1

                            } else
                                Toast.makeText(
                                    context,
                                    "You Reached The End Of The Quiz",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }, enabled = enabled, colors = ButtonDefaults.buttonColors(Color.Green)) {
                            Text(
                                text = "True",
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }
                        Button(
                            onClick = {
                                if (quizState.value < createList().size) {
                                    if (!createList()[quizState.value].answer) {
                                        scoreState.value = scoreState.value + 1
                                    }
                                    quizState.value = quizState.value + 1
                                } else
                                    Toast.makeText(
                                        context,
                                        "You Reached The End Of The Quiz",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }, enabled = enabled,
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(
                                text = "False",
                                style = TextStyle(fontSize = 18.sp),


                                )

                        }
                    }

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        val text = "Points ${scoreState.value} / ${createList().size}"
                        Text(
                            text = text,
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }


                    Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
                        Button(
                            onClick = {
                                scoreState.value = 0
                                quizState.value = 0
                            },
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Reset")
                        }
                    }

                }
            }
        }
    )
}









