package com.knomster.mobile_kindeev63.presentation.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knomster.mobile_kindeev63.domain.entities.PlaceCategory
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PlaceCategoryItem(
    placeCategory: PlaceCategory,
    height: Float
) {
    Surface(
        modifier = Modifier
            .height(height.dp)
            .padding(1.dp),
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .size((height / 4 * 3).dp),
                imageModel = { placeCategory.iconUrl },
                imageOptions = ImageOptions(contentScale = ContentScale.Inside),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Black
                    )
                },
                success = { _, painter ->
                    Icon(
                        modifier = Modifier
                            .size((height / 4 * 3).dp),
                        painter = painter,
                        contentDescription = "Category image",
                        tint = Color.Gray
                    )
                }
            )
            Text(
                text = placeCategory.name,
                lineHeight = (height / 2).sp,
                color = Color.Black,
                fontSize = (height / 2).sp,
            )
        }
    }
}