package com.example.composedemo.screen.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
){
    Box(
        modifier = Modifier.padding(top = 48.dp)
    ) {
        Text(
            text = "This is about screen",
            textAlign = TextAlign.Center,
        )
    }
}
