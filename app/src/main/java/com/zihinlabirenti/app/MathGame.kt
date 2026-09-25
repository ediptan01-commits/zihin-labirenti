package com.zihinlabirenti.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

data class MathQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

@Composable
fun MathGame() {

    val questions = remember {
        listOf(
            MathQuestion(
                "8 + 7 = ?",
                listOf("14", "15", "16", "17"),
                "15"
            ),
            MathQuestion(
                "12 × 3 = ?",
                listOf("32", "36", "38", "42"),
                "36"
            ),
            MathQuestion(
                "50 - 17 = ?",
                listOf("31", "32", "33", "34"),
                "33"
            ),
            MathQuestion(
                "72 ÷ 8 = ?",
                listOf("7", "8", "9", "10"),
                "9"
            ),
            MathQuestion(
                "5 + 3 × 2 = ?",
                listOf("11", "16", "13", "10"),
                "11"
            ),
            MathQuestion(
                "100 ÷ 4 + 5 = ?",
                listOf("25", "30", "35", "40"),
                "30"
            ),
            MathQuestion(
                "7 × 6 - 8 = ?",
                listOf("32", "34", "36", "38"),
                "34"
            ),
            MathQuestion(
                "3² + 4² = ?",
                listOf("20", "25", "27", "30"),
                "25"
            ),
            MathQuestion(
                "15 + 10 - 7 = ?",
                listOf("16", "17", "18", "19"),
                "18"
            ),
            MathQuestion(
                "90 ÷ 3 × 2 = ?",
                listOf("30", "45", "60", "90"),
                "60"
            )
        )
    }

    var questionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var combo by remember { mutableStateOf(0) }
    var lives by remember { mutableStateOf(3) }
    var timeLeft by remember { mutableStateOf(10) }
    var answered by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<Boolean?>(null) }
    var finished by remember { mutableStateOf(false) }

    val currentQuestion = questions[questionIndex]

    LaunchedEffect(questionIndex) {

        timeLeft = 10
        answered = false
        result = null

        while (timeLeft > 0 && !answered) {
            delay(1000)

            if (!answered) {
                timeLeft--
            }
        }

        if (!answered && timeLeft <= 0) {

            answered = true
            result = false
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

        MathResultScreen(
            score = score,
            onRestart = {
                questionIndex = 0
                score = 0
                combo = 0
                lives = 3
                timeLeft = 10
                answered = false
                result = null
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
            text = "🔢 MATEMATİK TESTİ",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

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
            text = currentQuestion.question,
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Kalan süre: $timeLeft",
            color = if (timeLeft <= 3)
                Color(0xFFFF5252)
            else
                Color(0xFF00D9FF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(35.dp))

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

                                result = isCorrect

                                if (isCorrect) {

                                    combo++

                                    val speedBonus = timeLeft * 15

                                    score +=
                                        100 +
                                        (combo * 25) +
                                        speedBonus

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

        when (result) {

            true -> {

                Text(
                    text = "✅ DOĞRU!",
                    color = Color(0xFF00E676),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                LaunchedEffect(questionIndex, result) {

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

                LaunchedEffect(questionIndex, result) {

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
fun MathResultScreen(
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
            text = "🔢",
            fontSize = 70.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "MATEMATİK TESTİ TAMAMLANDI",
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
