package com.example.overlayproject.my_composable_utils


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.overlayproject.R
import com.example.overlayproject.data
import com.example.overlayproject.ui.theme.mybackground_color



sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String) {
    object Home: BottomNavItem("home", Icons.Default.Home, "Home")
    object Scan: BottomNavItem("scan", Icons.Default.Menu, "Scam")
    object Cart: BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")

}

object CardDimensions {
    val Height = 390.dp
    val ImageHeight = 350.dp
    val Width = 120.dp
}

@Composable
fun HomeScreen(modifier: Modifier= Modifier, navController: NavController) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mybackground_color
    ) {

        val bottomnavController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = bottomnavController)},
            containerColor = mybackground_color) {
                innerPadding ->
            NavHost(
                navController = bottomnavController,
                startDestination = BottomNavItem.Home.route,
                modifier = Modifier
            ) {
                composable(BottomNavItem.Home.route) {  }
                composable(BottomNavItem.Scan.route) {  }
                composable(BottomNavItem.Cart.route) {  }
            }


            Column(
                modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 70.dp)) {
                // first Quarter Row
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        //ROW1
                        // containing the Cart icon
                        TopRow_1(modifier = Modifier, "Basil")

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
                    AccordionList(items = data, navController = navController)

                }
            }

        }
    }

}
@Composable
fun TopRow_1(modifier: Modifier, name: String) {

    Row(modifier = Modifier) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Hi $name",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Explore Designs",
                    fontSize = 10.sp)
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center) {


            Image(
                painter = painterResource(R.drawable.cartoon),
                contentDescription = "cartoon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(50.dp).clip(CircleShape)
                    .clickable(onClick = {}))
        }
    }
}


@Composable
fun SearchBar(modifier: Modifier) {
    var searchQuery by remember {mutableStateOf("")}
    OutlinedTextField(
        value = searchQuery,
        onValueChange = {searchQuery = it},
        leadingIcon = {Icon(Icons.Default.Search, contentDescription = "search Icon")},
        placeholder = {Text("Search")},
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(45.dp)

    )
}


@Composable
fun TopRow_2(modifier: Modifier) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
        //Column containing the search bar
        Column(modifier = Modifier.weight(3f)) {
            SearchBar(modifier = Modifier.fillMaxWidth())
        }
    }
}


@Composable
fun TopRow_3(modifier: Modifier) {
    Row(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start) {
            Text("Popular Designs", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End) {

            Text(
                "View All",
                fontSize = 10.sp,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.clickable(onClick = {}),
            )
        }
    }
}

@Composable
fun TopRow_4(modifier: Modifier) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)) {
        val categoryList = listOf("Antics", "Art", "Furniture", "Models")
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categoryList) {
                    item ->
                HorizontalListCardUI(item)
            }
        }
    }
}

@Composable
fun HorizontalListCardUI(item: String) {
    var isSelected by remember {mutableStateOf(false)}
    Card(
        modifier = Modifier
            .size(height = 50.dp, width = 100.dp)
            .clickable{isSelected = !isSelected},
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Black else mybackground_color
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Text(item,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black)
        }
    }
}

@Composable
fun AccordionList(items: Map<String, List<String>>, navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
        ) {
        items(items.entries.toList()) {(header, subItems) ->
            AccordionItem(header = header, subItems = subItems, navController = navController)
        }
    }
}


@Composable
fun AccordionItem(header: String, subItems: List<String>, navController: NavController) {
    var isExpanded by remember {mutableStateOf(false)}

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 100.dp)
            .width(CardDimensions.Width).height(CardDimensions.Height),
        colors = CardDefaults.cardColors(
            containerColor = mybackground_color
        )
        ) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(
                painter = painterResource(R.drawable.portland_vase),
                contentDescription = "vase",
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(onClick = {navController.navigate("product")})
                    .height(CardDimensions.ImageHeight),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("helvetican Vase", fontWeight = FontWeight.Normal)
        }
    }
}



@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Scan,
        BottomNavItem.Cart
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        var currentRoute = currentRoute(navController)

        items.forEach{item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {Text(item.label)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black
                )
            )
        }
    }
}