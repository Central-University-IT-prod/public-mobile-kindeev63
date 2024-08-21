package com.knomster.mobile_kindeev63.presentation.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knomster.mobile_kindeev63.domain.entities.PlaceData
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlaceItem(
    placeData: PlaceData,
    onClick: () -> Unit
) {
    val cardWidth = LocalConfiguration.current.screenWidthDp - 10
    val width = LocalConfiguration.current.screenWidthDp - 20
    val placeNameTextSize = width / 20
    val placeAddressTextSize = width / 25
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((cardWidth / 5 * 2).dp)
            .padding(5.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height((cardWidth / 5 * 2).dp),
            onClick = onClick,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier
                        .size((width / 5 * 2 - 10).dp),
                    imageModel = { placeData.photoUrl },
                    imageOptions = ImageOptions(contentScale = ContentScale.Inside),
                    loading = {
                        CircularProgressIndicator(
                            color = Color.Black
                        )
                    },
                    success = { _, painter ->
                        Image(
                            modifier = Modifier
                                .size((width / 5 * 2 - 10).dp)
                                .clip(RoundedCornerShape(10.dp)),
                            painter = painter,
                            contentScale = ContentScale.Crop,
                            contentDescription = "Place image",
                        )
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 5.dp)
                ) {
                    Text(
                        text = placeData.name,
                        fontSize = placeNameTextSize.sp,
                        lineHeight = placeNameTextSize.sp,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = placeData.address,
                        fontSize = placeAddressTextSize.sp,
                        lineHeight = placeAddressTextSize.sp,
                    )
                    FlowRow {
                        placeData.categories.forEach { category ->
                            PlaceCategoryItem(placeCategory = category, height = 20f)
                        }
                    }
                }
            }
        }
    }
}