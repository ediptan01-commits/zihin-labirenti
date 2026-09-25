package com.zihinlabirenti.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            ZihinLabirenti()
        }
    }
}

@Composable
fun ZihinLabirenti() {

    val arkaPlan = Color(0xFF080B16)
    val vurgu = Color(0xFF7C4DFF)

    MaterialTheme {

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
                fontSize = 72.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ZİHİN LABİRENTİ",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Zihnini test et.\nSınırlarını keşfet.",
                color = Color.LightGray,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    // İlk oyun ekranını burada açacağız.
                },
                modifier = Modifier
                    .height(58.dp),
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
}
