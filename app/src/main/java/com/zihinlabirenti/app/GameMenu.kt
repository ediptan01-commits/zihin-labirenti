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

@Composable
fun GameMenu() {

    var hafizaAcik by remember {
        mutableStateOf(false)
    }

    if (hafizaAcik) {

        MemoryGame()

        return
    }

    val background = Color(0xFF080B16)
    val purple = Color(0xFF7C4DFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(20.dp)
    ) {

        Text(
            text = "🧠 Zihin Labirenti",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bugün zihnini ne kadar zorlayacaksın?",
            color = Color.LightGray,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = purple
            )
        ) {
            Text(
                text = "⚡ GÜNÜN ZİHİN SAVAŞI",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Zeka Alanları",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            GameButton(
                emoji = "🧠",
                title = "Hafıza",
                modifier = Modifier.weight(1f),
                onClick = {
                    hafizaAcik = true
                }
            )

            GameButton(
                emoji = "💡",
                title = "Mantık",
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            GameButton(
                emoji = "🎯",
                title = "Dikkat",
                modifier = Modifier.weight(1f),
                onClick = {}
            )

            GameButton(
                emoji = "🔢",
                title = "Matematik",
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            GameButton(
                emoji = "🔷",
                title = "Örüntü",
                modifier = Modifier.weight(1f),
                onClick = {}
            )

            GameButton(
                emoji = "😈",
                title = "Ters Köşe",
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }
    }
}

@Composable
fun GameButton(
    emoji: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF151A2B)
        )
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = emoji,
                fontSize = 28.sp
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}
