package com.example.memento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Category(
    val name: String,
    val items: List<String>
)

@Composable
fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight =  FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(Color(150, 150, 150))
            .padding(16.dp)
    )
}

@Composable
fun CategoryItem(
    text: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Spacer(modifier = Modifier.weight(0.01f))
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .background(Color.White)
                .padding(20.dp)
        ){
            Text(
                text = text,
                fontSize = 14.sp,
                modifier = modifier
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.weight(0.01f))
    }
}