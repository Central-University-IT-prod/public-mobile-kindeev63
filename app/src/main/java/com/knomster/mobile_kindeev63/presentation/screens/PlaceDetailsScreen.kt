package com.knomster.mobile_kindeev63.presentation.screens

import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel
import com.knomster.mobile_kindeev63.presentation.ui.elements.PlaceCategoryItem
import com.knomster.mobile_kindeev63.presentation.viewModels.PlaceDetailsScreenViewModel
import com.knomster.mobile_kindeev63.presentation.viewModels.PlaceDetailsScreenViewModelFactory

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.domain.entities.DetailPlaceData
import com.knomster.mobile_kindeev63.domain.entities.PlaceCategory
import com.knomster.mobile_kindeev63.domain.entities.PlaceTip
import com.knomster.mobile_kindeev63.domain.entities.WorkTime
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PlaceDetailsScreen(
    mainViewModel: MainViewModel,
    placeId: String,
    navigateToNoteEditScreen: (Int) -> Unit,
    onBackPressed: () -> Unit
) {
    BackHandler(onBack = onBackPressed)
    val viewModel: PlaceDetailsScreenViewModel = viewModel(
        factory = PlaceDetailsScreenViewModelFactory(mainViewModel = mainViewModel)
    )
    val detailPlaceData by viewModel.detailPlaceData.observeAsState()
    if (detailPlaceData == null) {
        viewModel.setDetailPlaceData(placeId)
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Black
            )
        }
    } else {
        detailPlaceData?.let { placeData ->
            val screenWidth = LocalConfiguration.current.screenWidthDp
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                PlaceDetailsTopBar(
                    onBackPressed = onBackPressed,
                    viewModel = viewModel,
                    navigateToNoteEditScreen = navigateToNoteEditScreen
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    Text(
                        text = placeData.name,
                        color = Color.Black,
                        fontSize = (screenWidth / 15).sp,
                        fontWeight = FontWeight.Bold
                    )
                    placeData.photosUrls?.let { photos ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Photos(photoUrls = photos)
                    }
                    placeData.categories?.let { categories ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Categories(categories = categories)
                    }
                    placeData.description?.let { description ->
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "${stringResource(id = R.string.description)}:",
                            color = Color.Black,
                            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp,
                            lineHeight = (LocalConfiguration.current.screenWidthDp / 20 * 1.25).sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = description,
                            color = Color.Black,
                            fontSize = (LocalConfiguration.current.screenWidthDp / 25).sp,
                            lineHeight = (LocalConfiguration.current.screenWidthDp / 25 * 1.25).sp,
                        )
                    }
                    placeData.address?.let { address ->
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "${stringResource(id = R.string.address)}:",
                            color = Color.Black,
                            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp,
                            lineHeight = (LocalConfiguration.current.screenWidthDp / 20 * 1.25).sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = address,
                            color = Color.Black,
                            fontSize = (LocalConfiguration.current.screenWidthDp / 25).sp,
                            lineHeight = (LocalConfiguration.current.screenWidthDp / 25 * 1.25).sp,
                        )
                    }
                    placeData.workingTime?.let { workingTime ->
                        Spacer(modifier = Modifier.height(20.dp))
                        WorkingTime(workingTime = workingTime, openNow = placeData.isOpenNow)
                    }
                    ContactsBoxes(detailPlaceData = placeData)
                    AdditionalInformation(detailPlaceData = placeData)
                    placeData.tips?.let {
                        Spacer(modifier = Modifier.height(40.dp))
                        Tips(tips = placeData.tips)
                    }

                }
            }
        }
    }
}

@Composable
private fun PlaceDetailsTopBar(
    viewModel: PlaceDetailsScreenViewModel,
    navigateToNoteEditScreen: (Int) -> Unit,
    onBackPressed: () -> Unit
) {
    val notes by viewModel.mainViewModel.allNotes.observeAsState(emptyList())
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
        IconButton(onClick = onBackPressed) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.Black,
                contentDescription = "Back arrow"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                viewModel.makeNoteAnd(notes) { noteId ->
                    noteId?.let(navigateToNoteEditScreen)
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.icon_add_note),
                tint = Color.Black,
                contentDescription = "Add note"
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Photos(photoUrls: List<String>) {
    val photoWidth = LocalConfiguration.current.screenWidthDp - 40
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(count = photoUrls.size, state = pagerState) { index ->
            GlideImage(
                modifier = Modifier
                    .width(photoWidth.dp)
                    .height((photoWidth / 5 * 3).dp),
                imageModel = { photoUrls[index] },
                imageOptions = ImageOptions(contentScale = ContentScale.Inside),
                loading =
                {
                    Box(
                        modifier = Modifier
                            .width(photoWidth.dp)
                            .height((photoWidth / 5 * 3).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black
                        )
                    }
                },
                success = { _, painter ->
                    Image(
                        modifier = Modifier
                            .width(photoWidth.dp)
                            .height((photoWidth / 5 * 3).dp),
                        painter = painter,
                        contentScale = ContentScale.Fit,
                        contentDescription = "Place photo",
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        HorizontalPagerIndicator(pagerState = pagerState)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Categories(categories: List<PlaceCategory>) {
    FlowRow {
        categories.forEach { category ->
            PlaceCategoryItem(placeCategory = category, height = 30f)
        }
    }
}

@Composable
private fun WorkingTime(workingTime: List<WorkTime>, openNow: Boolean?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.work_time),
                color = Color.Black,
                fontSize = (LocalConfiguration.current.screenWidthDp / 15).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val weekday = stringArrayResource(id = R.array.weekday)
                workingTime.forEach { workTime ->
                    WorkTimeOnDay(
                        workingTime = workTime.workingTime.ifEmpty { stringResource(id = R.string.weekend) },
                        dayName = weekday[workTime.day - 1]
                    )
                    if (workTime.day != 7) HorizontalDivider(
                        color = Color.LightGray
                    )
                }
            }
            openNow?.let {
                Spacer(modifier = Modifier.height(10.dp))
                OpenNow(openNow = openNow)
            }
        }
    }
}

@Composable
private fun WorkTimeOnDay(workingTime: String, dayName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp),
            text = dayName,
            color = Color.Black,
            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(vertical = 2.dp),
            text = workingTime,
            color = Color.Black,
            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp
        )
    }
}

@Composable
private fun OpenNow(openNow: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "${stringResource(id = R.string.now)}:",
            color = Color.Black,
            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = if (openNow) R.string.open else R.string.closed),
            color = if (openNow) Color.Green else Color.Red,
            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContactsBoxes(detailPlaceData: DetailPlaceData) {
    if (
        detailPlaceData.menuUrl != null ||
        detailPlaceData.telephone != null ||
        detailPlaceData.email != null ||
        detailPlaceData.fax != null ||
        detailPlaceData.website != null
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        val context = LocalContext.current
        val webIntent = Intent(Intent.ACTION_VIEW)
        val callIntent = Intent(Intent.ACTION_DIAL)
        FlowRow {
            detailPlaceData.menuUrl?.let { menu ->
                ContactBox(
                    text = stringResource(id = R.string.menu),
                    iconResourceId = R.drawable.icon_menu
                ) {
                    context.startActivity(
                        webIntent.apply {
                            data = Uri.parse(menu)
                        }
                    )
                }
            }
            detailPlaceData.telephone?.let { telephone ->
                ContactBox(
                    text = stringResource(id = R.string.telephone),
                    iconResourceId = R.drawable.icon_telephone
                ) {
                    context.startActivity(
                        callIntent.apply {
                            data = Uri.parse("tel:$telephone")
                        }
                    )
                }
            }
            detailPlaceData.email?.let { email ->
                ContactBox(
                    text = stringResource(id = R.string.email),
                    iconResourceId = R.drawable.icon_email
                ) {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$email")
                    }
                    context.startActivity(emailIntent)
                }
            }
            detailPlaceData.fax?.let { fax ->
                ContactBox(
                    text = stringResource(id = R.string.fax),
                    iconResourceId = R.drawable.icon_fax
                ) {
                    context.startActivity(
                        callIntent.apply {
                            data = Uri.parse("tel:$fax")
                        }
                    )
                }
            }
            detailPlaceData.website?.let { website ->
                ContactBox(
                    text = stringResource(id = R.string.website),
                    iconResourceId = R.drawable.icon_website
                ) {
                    context.startActivity(
                        webIntent.apply {
                            data = Uri.parse(website)
                        }
                    )
                }
            }
        }
    }

}

@Composable
private fun ContactBox(
    text: String,
    iconResourceId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                vertical = 1.dp,
                horizontal = 3.dp
            )
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = onClick
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = iconResourceId),
                    contentDescription = "Contact icon"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = text,
                    color = Color.Black,
                    fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp
                )
            }
        }
    }
}

@Composable
private fun AdditionalInformation(detailPlaceData: DetailPlaceData) {
    if (
        detailPlaceData.music != null ||
        detailPlaceData.liveMusic != null ||
        detailPlaceData.delivery != null ||
        detailPlaceData.restroom != null ||
        detailPlaceData.privateRoom != null ||
        detailPlaceData.outdoorSeating != null ||
        detailPlaceData.wheelchairAccessible != null ||
        detailPlaceData.parking != null ||
        detailPlaceData.acceptCreditCard != null
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.additional_information),
                    color = Color.Black,
                    fontSize = (LocalConfiguration.current.screenWidthDp / 15).sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    detailPlaceData.music?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.music),
                            exist = exist
                        )
                    }
                    detailPlaceData.liveMusic?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.live_music),
                            exist = exist
                        )
                    }
                    detailPlaceData.delivery?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.delivery),
                            exist = exist
                        )
                    }
                    detailPlaceData.restroom?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.restroom),
                            exist = exist
                        )
                    }
                    detailPlaceData.privateRoom?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.private_room),
                            exist = exist
                        )
                    }
                    detailPlaceData.outdoorSeating?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.outdoor_seating),
                            exist = exist
                        )
                    }
                    detailPlaceData.wheelchairAccessible?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.wheelchair_accessible),
                            exist = exist
                        )
                    }
                    detailPlaceData.parking?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.parking),
                            exist = exist
                        )
                    }
                    detailPlaceData.acceptCreditCard?.let { exist ->
                        HorizontalDivider(color = Color.LightGray)
                        AdditionalInformationItem(
                            name = stringResource(id = R.string.accept_credit_card),
                            exist = exist
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun AdditionalInformationItem(name: String, exist: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth(0.7f),
            text = name,
            color = Color.Black,
            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(vertical = 2.dp),
            text = stringResource(id = if (exist) R.string.exist else R.string.no),
            color = Color.Black,
            fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp
        )
    }
}

@Composable
private fun Tips(tips: List<PlaceTip>) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "${stringResource(id = R.string.tips)}:",
                color = Color.Black,
                fontSize = (LocalConfiguration.current.screenWidthDp / 15).sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            tips.forEach { tip ->
                if (tip != tips.first()) HorizontalDivider(color = Color.LightGray)
                Tip(tip = tip)
            }
        }
    }
}

@Composable
private fun Tip(tip: PlaceTip) {
    val photoHeight = LocalConfiguration.current.screenWidthDp / 3
    Box(
        modifier = Modifier.padding(5.dp)
    ) {
        Column {
            tip.photoUrl?.let { photoUrl ->
                GlideImage(
                    imageModel = { photoUrl },
                    imageOptions = ImageOptions(contentScale = ContentScale.Inside),
                    loading =
                    {
                        Box(
                            modifier = Modifier
                                .height(photoHeight.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color.Black
                            )
                        }
                    },
                    success = { _, painter ->
                        Image(
                            modifier = Modifier
                                .height(photoHeight.dp),
                            painter = painter,
                            contentScale = ContentScale.Fit,
                            contentDescription = "Tip photo",
                        )
                    }
                )
            }
            Text(
                text = tip.text,
                fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp,
                lineHeight = (LocalConfiguration.current.screenWidthDp / 20 * 1.25).sp,
            )
            tip.createdAt?.let { createdDate ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = createdDate,
                    textAlign = TextAlign.End,
                    fontStyle = FontStyle.Italic,
                    fontSize = (LocalConfiguration.current.screenWidthDp / 30).sp,
                    color = Color.Gray
                )
            }
        }
    }
}