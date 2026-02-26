package com.example.overlayproject.my_composable_utils

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.overlayproject.CameraActivity
import com.example.overlayproject.R
import com.example.overlayproject.ui.theme.mybackground_color


object InspectionDimens {
    val padding = 10.dp
    val card_Evelation = 0.dp
    val tag_title = 15.sp
    val rounded_shape = 5.dp
}



@Composable
fun ProductPageUI(modifier:  Modifier = Modifier, navController: NavController){

    Scaffold(containerColor = mybackground_color) {
        paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().safeDrawingPadding(),
            ) {
            Row(modifier = Modifier.weight(1f)) {

                    Column(modifier=Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Image(
                            painter = painterResource(R.drawable.portland_vase),
                            contentDescription = "vase",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(200.dp).clip(RoundedCornerShape(
                                20.dp))
                            )
                    }
            }


            Row(
                modifier = Modifier.weight(1f)
                .padding(InspectionDimens.padding)){

                Column {
                    Row(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier.weight(1f).padding(end = 5.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ClickableTab(tabId = 1)
                        }

                        Column(
                            modifier = Modifier.weight(1f).padding(end = 5.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ClickableTab(tabId = 2)
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            ClickableTab(tabId = 3)
                        }
                    }

                    //Text Content
                    Row(modifier = Modifier.weight(7f).padding(10.dp)){

                        Column(
                            modifier = Modifier
                        ) {

                        Row(modifier = Modifier.fillMaxWidth().weight(2f)) {
                            Text(
                                "This Vase is a Helvetican Vase best suited for Home Decor, Made with Ceramic engravings and Wonderful for " +
                                "home visits",
                                fontSize = 15.sp)
                        }

                            Row(modifier = Modifier.fillMaxWidth().weight(3f)) {

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            stringResource(R.string.ratings),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                        Text(
                                            stringResource(R.string.price),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }

                                    Column(
                                        modifier = Modifier.weight(3f)
                                    ) {
                                        Text(
                                            ": Ratings 5+",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                        Text(
                                            ": 300",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }
                            }


                                  Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                      val context = LocalContext.current
                                      Button(
                                          modifier = Modifier.fillMaxWidth(),
                                          onClick = {
                                              val intent = Intent(context, CameraActivity::class.java)
                                              context.startActivity(intent)

                                          },
                                          shape = RoundedCornerShape(InspectionDimens.rounded_shape),
                                          colors = ButtonColors(Color.Black,
                                              contentColor = mybackground_color,
                                              disabledContentColor = mybackground_color,
                                              disabledContainerColor = Color.Red)
                                      ) {
                                          Text(stringResource(R.string.get_button))
                                      }

                                  }
                                }
                        }
                    }

            }
        }
    }
}



@Composable
fun ClickableTab(modifier: Modifier = Modifier, tabId: Int) {

    var isSelected by remember { mutableStateOf(false) }
    val tabTitle: String =  if (tabId == 1) stringResource(R.string.overview)
                            else if (tabId == 2) stringResource(R.string.three_d_view)
                            else stringResource(R.string.overlay)

    Card(
        colors = CardDefaults.cardColors(if (!isSelected) mybackground_color else colorResource(R.color.black)),
        modifier = Modifier.clickable(onClick = {isSelected = !isSelected}),
        shape = RoundedCornerShape(InspectionDimens.rounded_shape),
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Text(
                tabTitle,
                fontWeight = FontWeight.Bold,
                fontSize = InspectionDimens.tag_title,
                color = if (!isSelected) Color.Black else mybackground_color)
        }
    }
}