package com.example.overlayproject


import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.overlayproject.my_composable_utils.ProductPageUI
import com.example.overlayproject.my_composable_utils.LaunchScreen
import com.example.overlayproject.my_composable_utils.HomeScreen
import com.example.overlayproject.ui.theme.OverlayProjectTheme
import com.example.overlayproject.ui.theme.mybackground_color

val data = mapOf(
    "Fruits" to listOf("Apple", "Banana", "Mango", "Grape", "Orange"),
    "Veggies" to listOf("Carrot", "Broccoli", "Spinach", "Tomato"),
    "Drinks" to listOf("Water", "Juice", "Soda", "Tea", "Coffee")
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OverlayProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = mybackground_color
                    ) {
                      MyApp()
                }
            }
        }
    }
}


@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "launchScreen"
    ) {
        composable ("launchScreen"){
            LaunchScreen(navController = navController)
        }
        composable ("home"){
           HomeScreen(navController = navController)
        }
        composable ("product"){
            ProductPageUI(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOverlayUI() {
    val navController = rememberNavController()
    OverlayProjectTheme {
        HomeScreen(navController = navController)
    }
}