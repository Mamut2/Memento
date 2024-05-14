package com.example.memento

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memento.ui.theme.MementoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MementoTheme {
                MainUI()
            }
        }
    }
}

@Composable
fun MainUI() {
    Scaffold (
        containerColor = Color.DarkGray,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                shape = CircleShape,
                containerColor = Color.Green
            ){
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },

        bottomBar = {
            BottomAppBar(
                containerColor = Color.Black,
                contentPadding = PaddingValues(0.dp)
            ) {
                Row {
                    Button(
                        onClick = { /* do something */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(7, 147, 57)),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f, true)
                            .clip(RoundedCornerShape(0.dp, 0.dp, 0.dp, 30.dp))
                            .padding(0.dp, 0.dp, 0.5.dp, 0.dp),
                        shape = RectangleShape

                    ){
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }

                    Button(
                        onClick = { /* do something */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(7, 147, 57)),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f, true)
                            .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 0.dp))
                            .padding(0.5.dp, 0.dp, 0.dp, 0.dp),
                        shape = RectangleShape

                    ){
                        Icon(Icons.Filled.Edit, contentDescription = "Localized description")
                    }

                }
            }
        }
    ){  innerPadding ->
        val taskList = listOf(
            Category(
                name = "To-Do",
                items = tasks
            ),

            Category(
                name = "Done",
                items = doneTasks
            )
        )
        CategorizedLazyColumn(categories = taskList, modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MementoTheme {
        MainUI()
    }
}
