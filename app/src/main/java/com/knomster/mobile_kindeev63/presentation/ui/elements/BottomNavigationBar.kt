package com.knomster.mobile_kindeev63.presentation.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.knomster.mobile_kindeev63.presentation.entities.BottomNavigationBarItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationBarItem>,
    selectedItemId: Int,
    onItemSelected: (BottomNavigationBarItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        items.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        onItemSelected(item)
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = item.drawableId),
                    contentDescription = "Bottom navigation item icon",
                    colorFilter = if (item.id == selectedItemId) null else ColorFilter.tint(Color.Gray)
                )
            }
        }
    }
}