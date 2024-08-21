package com.knomster.mobile_kindeev63.presentation.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.knomster.mobile_kindeev63.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MainHeader(name: String?, photoUrl: String?, goToAccount: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(elevation = 2.dp)
            .background(Color.White)
            .padding(horizontal = 10.dp)
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        name?.let {
            Text(
                text = stringResource(id = R.string.hello_text) + " $it"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        GlideImage(
            imageModel = { photoUrl },
            imageOptions = ImageOptions(contentScale = ContentScale.Inside),
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = Color.Black
                )
            },
            success = { _, painter ->
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable(onClick = goToAccount),
                    painter = painter,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Account icon",
                )
            },
            failure = {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable(onClick = goToAccount),
                    painter = painterResource(id = R.drawable.ic_account_circle),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Account icon",
                )
            }
        )
    }
}