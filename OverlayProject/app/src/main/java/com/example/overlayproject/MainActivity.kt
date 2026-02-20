package com.example.overlayproject
import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.overlayproject.my_composable_utils.TopRow_1
import com.example.overlayproject.my_composable_utils.TopRow_2
import com.example.overlayproject.my_composable_utils.TopRow_3
import com.example.overlayproject.my_composable_utils.TopRow_4
import com.example.overlayproject.my_composable_utils.AccordionList
import com.example.overlayproject.my_composable_utils.BottomNavigationBar
import com.example.overlayproject.ui.theme.OverlayProjectTheme
import com.example.overlayproject.ui.theme.mybackground_color

val data = mapOf(
    "Fruits" to listOf("Apple", "Banana", "Mango", "Grape", "Orange"),
    "Veggies" to listOf("Carrot", "Broccoli", "Spinach", "Tomato"),
    "Drinks" to listOf("Water", "Juice", "Soda", "Tea", "Coffee")
)


sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String) {
    object Home: BottomNavItem("home", Icons.Default.Home, "Home")
    object Scan: BottomNavItem("scan", Icons.Default.Menu, "Scam")
    object Cart: BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")

}
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
                    MainUi()
                }

            }
        }
    }
}




@Composable
fun MainUi(modifier: Modifier= Modifier) {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController)},
        containerColor = mybackground_color) {
        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier
            ) {
            composable(BottomNavItem.Home.route) {  }
            composable(BottomNavItem.Scan.route) {  }
            composable(BottomNavItem.Cart.route) {}
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp)) {
            // first Quarter Row
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    //ROW1
                    // containing the Cart icon
                    TopRow_1(modifier = Modifier, "Churchil")

                    //Row2
                    // containiing the Search Bar and the Scam Button
                    TopRow_2(modifier = Modifier)

                    //Row 3
                    TopRow_3(modifier = Modifier)
                    //ROW 4
                    //contains the horizontal list containing the tags
                    TopRow_4(modifier = Modifier)

                }
            }

            //second 2-Quarter section
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()) {
                AccordionList(items = data)

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewOverlayUI() {

    OverlayProjectTheme {
        MainUi()
    }
}