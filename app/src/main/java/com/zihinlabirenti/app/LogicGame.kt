package com.zihinlabirenti.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class LogicQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

@Composable
fun LogicGame() {

    val questions = remember {

        listOf(

            LogicQuestion(
                question = "2, 4, 6, 8, ?",
                options = listOf("9", "10", "11", "12"),
                correctAnswer = "10"
            ),

            LogicQuestion(
                question = "3, 6, 12, 24, ?",
                options = listOf("36", "42", "48", "54"),
                correctAnswer = "48"
            ),

            LogicQuestion(
                question = "🔴 🔵 🔴 🔵 ?",
                options = listOf("🔴", "🟢", "🟡", "🟣"),
                correctAnswer = "🔴"
            ),

            LogicQuestion(
                question = "5, 10, 15, 20, ?",
                options = listOf("21", "23", "25", "30"),
                correctAnswer = "25"
            ),

            LogicQuestion(
                question = "1, 1, 2, 3, 5, ?",
                options = listOf("6", "7", "8", "9"),
                correctAnswer = "8"
            ),

            LogicQuestion(
                question = "10, 9, 7, 4, ?",
                options = listOf("2", "1", "0", "-1"),
                correctAnswer = "0"
            ),

            LogicQuestion(
                question = "🔺 🔵 🔺 🔵 🔺 ?",
                options = listOf("🔺", "🔵", "🟢", "🟡"),
                correctAnswer = "🔵"
            ),

            LogicQuestion(
                question = "100, 50, 25, 12.5, ?",
                options = listOf("10", "8", "6.25", "5"),
                correctAnswer = "6.25"
            ),

            LogicQuestion(
                question = "7, 14, 21, 28, ?",
                options = listOf("32", "35", "36", "42"),
                correctAnswer = "35"
            ),

            LogicQuestion(
                question = "1, 4, 9, 16, ?",
                options = listOf("20", "24", "25", "30"),
                correctAnswer = "25"
            )
        )
    }

    var questionIndex by remember {
        mutableStateOf(0)
    }

    var score by remember {
        mutableStateOf(0)
    }

    var combo by remember {
        mutableStateOf(0)
    }

    var lives by remember {
        mutableStateOf(3)
    }

    var timeLeft by remember {
        mutableStateOf(10)
    }

    var answered by remember {
        mutableStateOf(false)
    }

    var correct by remember {
        mutableStateOf<Boolean?>(null)
    }

    var finished by remember {
        mutableStateOf(false)
    }

    val currentQuestion = questions[questionIndex]

    LaunchedEffect(questionIndex) {

        timeLeft = 10
        answered = false
        correct = null

        while (timeLeft > 0 && !answered) {

            delay(1000)

            if (!answered) {
                timeLeft--
            }
        }

        if (!answered && timeLeft <= 0) {

            answered = true
            correct = false
            combo = 0
            lives--

            delay(800)

            if (questionIndex < questions.lastIndex && lives > 0) {
                questionIndex++
            } else {
                finished = true
            }
        }
    }

    if (finished) {

        LogicResultScreen(
            score = score,
            onRestart = {
                questionIndex = 0
                score = 0
                combo = 0
                lives = 3
                timeLeft = 10
                answered = false
                correct = null
                finished = false
            }
        )

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF080B16))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "💡 MANTIK TESTİ",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Soru ${questionIndex + 1}/10",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Text(
                text = "❤️".repeat(lives),
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Puan: $score    🔥 Combo: $combo",
            color = Color(0xFFB388FF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "Sıradaki ne?",
            color = Color.LightGray,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = currentQuestion.question,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Kalan süre: $timeLeft",
            color = if (timeLeft <= 3)
                Color(0xFFFF5252)
            else
                Color(0xFF00D9FF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        currentQuestion.options.chunked(2).forEach { row ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                row.forEach { option ->

                    Button(
                        onClick = {

                            if (!answered) {

                                answered = true

                                val isCorrect =
                                    option == currentQuestion.correctAnswer

                                correct = isCorrect

                                if (isCorrect) {

                                    combo++

                                    score += 100 + (combo * 25)

                                } else {

                                    combo = 0
                                    lives--
                                }
                            }
                        },
                        enabled = !answered,
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF151A2B)
                        )
                    ) {

                        Text(
                            text = option,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (correct) {

            true -> {

                Text(
                    text = "✅ DOĞRU!",
                    color = Color(0xFF00E676),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                LaunchedEffect(questionIndex, correct) {

                    delay(800)

                    if (questionIndex < questions.lastIndex) {
                        questionIndex++
                    } else {
                        finished = true
                    }
                }
            }

            false -> {

                Text(
                    text = "❌ YANLIŞ!",
                    color = Color(0xFFFF5252),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                LaunchedEffect(questionIndex, correct) {

                    delay(800)

                    if (questionIndex < questions.lastIndex && lives > 0) {
                        questionIndex++
                    } else {
                        finished = true
                    }
                }
            }

            null -> Unit
        }
    }
}

@Composable
fun LogicResultScreen(
    score: Int,
    onRestart: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF080B16))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🧩",
            fontSize = 70.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "MANTIK TESTİ TAMAMLANDI",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Toplam Puan",
            color = Color.LightGray,
            fontSize = 16.sp
        )

        Text(
            text = "$score",
            color = Color(0xFFB388FF),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C4DFF)
            )
        ) {

            Text(
                text = "TEKRAR OYNA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
