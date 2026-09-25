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
import kotlin.math.max

data class AttentionQuestion(
    val symbols: List<String>,
    val differentIndex: Int
)

@Composable
fun AttentionGame() {

    val questions = remember {
        listOf(
            AttentionQuestion(
                listOf("🔵", "🔵", "🔵", "🔴"),
                3
            ),
            AttentionQuestion(
                listOf("⭐", "⭐", "🌟", "⭐"),
                2
            ),
            AttentionQuestion(
                listOf("🟢", "🟢", "🔵", "🟢"),
                2
            ),
            AttentionQuestion(
                listOf("🔺", "🔺", "🔻", "🔺"),
                2
            ),
            AttentionQuestion(
                listOf("❤️", "❤️", "🧡", "❤️"),
                2
            ),
            AttentionQuestion(
                listOf("🟡", "🟡", "🟡", "🟠"),
                3
            ),
            AttentionQuestion(
                listOf("⬛", "⬛", "⬜", "⬛"),
                2
            ),
            AttentionQuestion(
                listOf("🔷", "🔷", "🔶", "🔷"),
                2
            ),
            AttentionQuestion(
                listOf("🌙", "🌙", "☀️", "🌙"),
                2
            ),
            AttentionQuestion(
                listOf("🟣", "🟣", "🟣", "🔵"),
                3
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
        mutableStateOf(7)
    }

    var answered by remember {
        mutableStateOf(false)
    }

    var result by remember {
        mutableStateOf<Boolean?>(null)
    }

    var finished by remember {
        mutableStateOf(false)
    }

    val currentQuestion = questions[questionIndex]

    LaunchedEffect(questionIndex) {

        timeLeft = 7
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

        AttentionResultScreen(
            score = score,
            onRestart = {
                questionIndex = 0
                score = 0
                combo = 0
                lives = 3
                timeLeft = 7
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
            text = "🎯 DİKKAT TESTİ",
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

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Puan: $score    🔥 Combo: $combo",
            color = Color(0xFFB388FF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Farklı olanı bul!",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Kalan süre: $timeLeft",
            color = if (timeLeft <= 2)
                Color(0xFFFF5252)
            else
                Color(0xFF00D9FF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        currentQuestion.symbols.chunked(2).forEachIndexed { rowIndex, row ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                row.forEachIndexed { columnIndex, symbol ->

                    val realIndex = rowIndex * 2 + columnIndex

                    Button(
                        onClick = {

                            if (!answered) {

                                answered = true

                                val isCorrect =
                                    realIndex == currentQuestion.differentIndex

                                result = isCorrect

                                if (isCorrect) {

                                    combo++

                                    val speedBonus = timeLeft * 20

                                    score += 100 + (combo * 25) + speedBonus

                                } else {

                                    combo = 0
                                    lives--
                                }
                            }
                        },
                        enabled = !answered,
                        modifier = Modifier
                            .weight(1f)
                            .height(105.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF151A2B)
                        )
                    ) {

                        Text(
                            text = symbol,
                            fontSize = 42.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
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
fun AttentionResultScreen(
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
            text = "🎯",
            fontSize = 70.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "DİKKAT TESTİ TAMAMLANDI",
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
