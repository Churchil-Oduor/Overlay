package com.example.overlayproject.my_composable_utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.overlayproject.R
import com.example.overlayproject.ui.theme.mybackground_color
import kotlinx.coroutines.delay


@Composable
fun LaunchScreen(modifier: Modifier = Modifier, navController: NavController) {


    Scaffold(containerColor = mybackground_color) {
        innerPadding ->

        LaunchedEffect(Unit) {
            delay(900)
            navController.navigate("home") {
                popUpTo("launchScreen") {inclusive = true}
            }

        }
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Image(painter = painterResource(R.drawable.logo1),
                contentDescription = "logo",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }
    }

}