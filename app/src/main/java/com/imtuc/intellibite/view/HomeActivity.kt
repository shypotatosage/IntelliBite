package com.imtuc.intellibite.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.imtuc.intellibite.navigation.Screen
import com.imtuc.intellibite.ui.theme.DarkGreen

@Composable
fun HomeActivity(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                navController.navigate(Screen.InputIngredients.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp, 32.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.RestaurantMenu,
                    contentDescription = "Search Recipes",
                    tint = DarkGreen,
                    modifier = Modifier.size(75.dp)
                )
                Text(
                    text = "Recipes For You",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = DarkGreen,
                    modifier = Modifier
                        .padding(12.dp, 16.dp, 12.dp, 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                navController.navigate(Screen.ImageClassification.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp, 32.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Classify Fruit/Vegetables",
                    tint = Color.White,
                    modifier = Modifier.size(75.dp)
                )
                Text(
                    text = "Fruit/Vegetables Nutrition",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(12.dp, 16.dp, 12.dp, 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}