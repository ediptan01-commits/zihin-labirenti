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

data class MemoryQuestion(
    val sequence: List<String>,
    val targetPosition: Int
)

@Composable
fun MemoryGame() {

    val questions = remember {
        listOf(
            MemoryQuestion(
                listOf("🔴", "🔵", "🟢", "🟡"),
                1
            ),
            MemoryQuestion(
                listOf("⭐", "❤️", "🔷", "🌙"),
                2
            ),
            MemoryQuestion(
                listOf("🟢", "🟣", "🔴", "⭐"),
                0
            ),
            MemoryQuestion(
                listOf("🔵", "🟡", "❤️", "🟢"),
                3
            ),
            MemoryQuestion(
                listOf("🌙", "⭐", "🔴", "🔷"),
                0
            ),
            MemoryQuestion(
                listOf("❤️", "🟢", "🟡", "🔵"),
                2
            ),
            MemoryQuestion(
                listOf("🔷", "⭐", "🟣", "❤️"),
                3
            ),
            MemoryQuestion(
                listOf("🟡", "🔴", "🌙", "🟢"),
                1
            ),
            MemoryQuestion(
                listOf("🟣", "🔵", "⭐", "🟡"),
                2
            ),
            MemoryQuestion(
                listOf("❤️", "🔷", "🟢", "🌙"),
                3
            )
        )
    }

    var questionIndex by remember {
        mutableStateOf(0)
    }

    var showingSequence by remember {
        mutableStateOf(true)
    }

    var timeLeft by remember {
        mutableStateOf(6)
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

    var answered by remember {
        mutableStateOf(false)
    }

    var lastCorrect by remember {
        mutableStateOf<Boolean?>(null)
    }

    var gameFinished by remember {
        mutableStateOf(false)
    }

    val currentQuestion = questions[questionIndex]

    /*
     * Hafıza aşaması
     */
    LaunchedEffect(questionIndex) {

        showingSequence = true
        answered = false
        lastCorrect = null
        timeLeft = 6

        delay(2500)

        showingSequence = false

        while (timeLeft > 0 && !answered) {

            delay(1000)

            if (!answered) {
                timeLeft--
            }
        }

        if (!answered && !showingSequence && timeLeft <= 0) {

            answered = true
            lastCorrect = false
            combo = 0
            lives--

            delay(800)

            if (questionIndex < questions.lastIndex && lives > 0) {
                questionIndex++
            } else {
                gameFinished = true
            }
        }
    }

    /*
     * Sonuç ekranı
     */
    if (gameFinished) {

        ResultScreen(
            score = score,
            combo = combo,
            onRestart = {
                questionIndex = 0
                score = 0
                combo = 0
                lives = 3
                answered = false
                lastCorrect = null
                gameFinished = false
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
            text = "🧠 HAFIZA TESTİ",
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

        Spacer(modifier = Modifier.height(50.dp))

        if (showingSequence) {

            Text(
                text = "Sıralamayı aklında tut!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(35.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                currentQuestion.sequence.forEachIndexed { index, symbol ->

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = symbol,
                            fontSize = 42.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "${index + 1}",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "2,5 saniye...",
                color = Color(0xFF00D9FF),
                fontSize = 16.sp
            )

        } else {

            Text(
                text = "${currentQuestion.targetPosition + 1}. sembol neydi?",
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

            Spacer(modifier = Modifier.height(30.dp))

            val options = remember(questionIndex) {
                listOf(
                    currentQuestion.sequence[currentQuestion.targetPosition],
                    "⬛",
                    "🔶",
                    "🟦"
                ).shuffled()
            }

            options.chunked(2).forEach { rowOptions ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    rowOptions.forEach { option ->

                        Button(
                            onClick = {

                                if (!answered) {

                                    answered = true

                                    val correct =
                                        option == currentQuestion.sequence[
                                            currentQuestion.targetPosition
                                        ]

                                    lastCorrect = correct

                                    if (correct) {

                                        combo++

                                        score += 100 + (combo * 20)

                                    } else {

                                        combo = 0
                                        lives--
                                    }

                                }
                            },
                            enabled = !answered,
                            modifier = Modifier
                                .weight(1f)
                                .height(75.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF151A2B)
                            )
                        ) {

                            Text(
                                text = option,
                                fontSize = 32.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            when (lastCorrect) {

                true -> {

                    Text(
                        text = "✅ DOĞRU!",
                        color = Color(0xFF00E676),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    LaunchedEffect(questionIndex, lastCorrect) {

                        delay(800)

                        if (questionIndex < questions.lastIndex) {
                            questionIndex++
                        } else {
                            gameFinished = true
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

                    LaunchedEffect(questionIndex, lastCorrect) {

                        delay(800)

                        if (questionIndex < questions.lastIndex && lives > 0) {
                            questionIndex++
                        } else {
                            gameFinished = true
                        }
                    }
                }

                null -> Unit
            }
        }
    }
}

@Composable
fun ResultScreen(
    score: Int,
    combo: Int,
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
            text = "🏆",
            fontSize = 70.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "TEST TAMAMLANDI",
            color = Color.White,
            fontSize = 28.sp,
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

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "En yüksek Combo: $combo",
            color = Color.White,
            fontSize = 18.sp
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
