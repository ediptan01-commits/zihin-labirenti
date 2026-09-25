package com.zihinlabirenti.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZihinLabirentiApp()
        }
    }
}

@Composable
fun ZihinLabirentiApp() {

    var menuAcik by remember { mutableStateOf(false) }

    if (menuAcik) {

        GameMenu()

    } else {

        AnaEkran(
            onStart = {
                menuAcik = true
            }
        )
    }
}

@Composable
fun AnaEkran(
    onStart: () -> Unit
) {

    val arkaPlan = Color(0xFF080B16)
    val vurgu = Color(0xFF7C4DFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(arkaPlan)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🧠",
            fontSize = 80.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "ZİHİN LABİRENTİ",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Zihnini test et.\nSınırlarını keşfet.",
            color = Color.LightGray,
            fontSize = 17.sp,
            lineHeight = 25.sp
        )

        Spacer(modifier = Modifier.height(45.dp))

        Button(
            onClick = onStart,
            modifier = Modifier
                .height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = vurgu
            )
        ) {

            Text(
                text = "OYUNA BAŞLA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
    }
}
