package com.knomster.mobile_kindeev63.presentation.ui.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.knomster.mobile_kindeev63.R

@Composable
fun PagePickRow(
    pagesCount: Int,
    width: Float,
    currentPage: Int,
    onPickPage: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(currentPage) {
        scrollState.animateScrollToItem(currentPage - 1)
    }

    Row(
        modifier = Modifier.width(width.dp)
    ) {
        PageIconButton(resourceId = R.drawable.ic_first_page) {
            onPickPage(1)
        }
        LazyRow(
            modifier = Modifier.weight(1f),
            state = scrollState
        ) {
            items(
                items = (1..pagesCount).toList()
            ) { page ->
                PageButton(
                    text = page.toString(),
                    picked = currentPage == page
                ) {
                    onPickPage(page)
                }
            }
        }
        PageIconButton(resourceId = R.drawable.ic_last_page) {
            onPickPage(pagesCount)
        }
    }

}

@Composable
private fun PageButton(
    text: String,
    picked: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .size(50.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (picked) Color.LightGray else Color.White)
    ) {
        Text(
            text = text,
            color = if (picked) Color.Black else Color.Gray
        )
    }
}

@Composable
private fun PageIconButton(
    resourceId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .size(50.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White)
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = painterResource(id = resourceId),
            contentDescription = "Page icon",
            tint = Color.Black
        )
    }
}