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

@Composable
fun MemoryGame() {

    val symbols = listOf("🔴", "🔵", "🟢", "🟡")

    var gösteriliyor by remember {
        mutableStateOf(true)
    }

    var doğruCevap by remember {
        mutableStateOf("🔵")
    }

    var puan by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {

        delay(2500)

        gösteriliyor = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF080B16))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🧠 HAFIZA TESTİ",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (gösteriliyor) {

            Text(
                text = "Sembolleri aklında tut!",
                color = Color.LightGray,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                symbols.forEach {

                    Text(
                        text = it,
                        fontSize = 45.sp
                    )
                }
            }

        } else {

            Text(
                text = "Hangisi vardı?",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                symbols.shuffled().forEach { symbol ->

                    Button(
                        onClick = {

                            if (symbol == doğruCevap) {
                                puan += 100
                            }

                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF151A2B)
                        )
                    ) {

                        Text(
                            text = symbol,
                            fontSize = 32.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Puan: $puan",
                color = Color(0xFFB388FF),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
