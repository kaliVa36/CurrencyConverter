package com.currencyconverter.app.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DummyScreen(color: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(color)) {
      Box(Modifier.size(100.dp).background(Color.Gray).clickable { onClick() })
    }
}