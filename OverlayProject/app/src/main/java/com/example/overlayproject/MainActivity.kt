package com.example.overlayproject
import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.overlayproject.my_composable_utils.BottomNavItem
import com.example.overlayproject.my_composable_utils.BottomNavigationBar
import com.example.overlayproject.my_composable_utils.InspectionPageUI
import com.example.overlayproject.my_composable_utils.MainUI
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

                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController = navController)},
                        containerColor = mybackground_color) {
                            innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavItem.Home.route,
                            modifier = Modifier
                        ) {
                            composable(BottomNavItem.Home.route) {  }
                            composable(BottomNavItem.Scan.route) {  }
                            composable(BottomNavItem.Cart.route) {  }
                        }

                     //   MainUI()
                        InspectionPageUI()
                    }

                }

            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewOverlayUI() {

    OverlayProjectTheme {
//        MainUi()
      //  LaunchScreen()
        InspectionPageUI()
    }
}