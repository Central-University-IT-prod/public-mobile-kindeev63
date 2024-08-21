package com.knomster.mobile_kindeev63.presentation.screens

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.presentation.ui.elements.DatePickerDialog
import com.knomster.mobile_kindeev63.presentation.entities.NoteState
import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel
import com.knomster.mobile_kindeev63.presentation.viewModels.NoteEditScreenViewModel
import com.knomster.mobile_kindeev63.presentation.viewModels.NoteEditScreenViewModelFactory
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

@Composable
fun NoteEditScreen(
    mainViewModel: MainViewModel,
    noteId: Int,
    navigateToPlaceDetailsScreen: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val viewModel: NoteEditScreenViewModel = viewModel(
        factory = NoteEditScreenViewModelFactory(mainViewModel = mainViewModel, noteId = noteId)
    )
    val noteState by viewModel.currentNoteState.observeAsState(NoteState.empty)
    val showDatePickerDialog by viewModel.showDatePickerDialog.observeAsState(false)
    if (showDatePickerDialog) {
        DatePickerDialog(
            time = noteState.date ?: System.currentTimeMillis(),
            weekDaysNames = stringArrayResource(id = R.array.short_weekday).toList(),
            saveButtonText = stringResource(id = R.string.save),
            cancelButtonText = stringResource(id = R.string.cancel),
            onCloseDialog = { viewModel.closeDatePickerDialog() },
            onPick = { year, month, day ->
                val date =
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(System.currentTimeMillis()),
                        ZoneId.systemDefault()
                    ).atZone(
                        ZoneId.systemDefault()
                    ).withYear(year).withMonth(month).withDayOfMonth(day)
                viewModel.addNoteState(
                    date = date.toInstant().toEpochMilli()
                )
            }
        )
    }
    BackHandler { viewModel.saveNoteAnd(onBackPressed) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NoteEditTopBar(
            viewModel = viewModel,
            onBackPressed = { viewModel.saveNoteAnd(onBackPressed) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        NoteTitleAndPinnedPlace(
            viewModel = viewModel,
            navigateToPlaceDetailsScreen = navigateToPlaceDetailsScreen
        )
        NoteDate(viewModel = viewModel)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            NoteText(viewModel = viewModel)
        }
    }
}

@Composable
fun NoteTitleAndPinnedPlace(
    viewModel: NoteEditScreenViewModel,
    navigateToPlaceDetailsScreen: (String) -> Unit
) {
    val noteState by viewModel.currentNoteState.observeAsState(NoteState.empty)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        ) {
            BasicTextField(
                value = noteState.title,
                onValueChange = { viewModel.addNoteState(title = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 20.sp)
            )
            if (noteState.title.text.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.title),
                    style = TextStyle(color = Color.Gray),
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 20.sp
                )
            }
        }
        noteState.pinnedPlaceId?.let { placeId ->
            IconButton(onClick = { navigateToPlaceDetailsScreen(placeId) }) {
                Image(
                    painter = painterResource(id = R.drawable.icon_place),
                    contentDescription = "Pinned place icon"
                )
            }
        }
    }
}

@Composable
fun NoteText(viewModel: NoteEditScreenViewModel) {
    val noteState by viewModel.currentNoteState.observeAsState(NoteState.empty)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        BasicTextField(
            value = noteState.text,
            onValueChange = { viewModel.addNoteState(text = it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle.Default.copy(fontSize = 20.sp)
        )
        if (noteState.text.text.isEmpty()) {
            Text(
                text = stringResource(id = R.string.text),
                style = TextStyle(color = Color.Gray),
                modifier = Modifier.padding(start = 4.dp),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun NoteDate(
    viewModel: NoteEditScreenViewModel
) {
    val noteState by viewModel.currentNoteState.observeAsState(NoteState.empty)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Card(
            modifier = Modifier
                .padding(
                    horizontal = 5.dp,
                    vertical = 2.dp
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = { viewModel.showDatePickerDialog() }
        ) {
            val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            Text(
                modifier = Modifier.padding(5.dp),
                text = if (noteState.date == null) {
                    stringResource(id = R.string.no_date)
                } else {
                    dateFormatter.format(noteState.date)
                },
                color = Color.Black,
                fontSize = (LocalConfiguration.current.screenWidthDp / 15).sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NoteEditTopBar(
    viewModel: NoteEditScreenViewModel,
    onBackPressed: () -> Unit,
) {
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
        IconButtonWithOnTouch(
            iconResourceId = R.drawable.ic_undo,
            action = { viewModel.undoState() }
        )
        IconButtonWithOnTouch(
            iconResourceId = R.drawable.ic_redo,
            action = { viewModel.redoState() }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IconButtonWithOnTouch(iconResourceId: Int, action: () -> Unit) {
    var isPressed by rememberSaveable { mutableStateOf(false) }
    val colorTransparent by animateColorAsState(
        targetValue = Color.Transparent,
        animationSpec = spring(), label = "transparent"
    )
    val colorGray by animateColorAsState(
        targetValue = Color.Black.copy(alpha = 0.2f),
        animationSpec = spring(), label = "gray"
    )
    var color by remember {
        mutableStateOf(colorTransparent)
    }
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = color, shape = CircleShape)
            .pointerInteropFilter(
                onTouchEvent = { event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isPressed = true
                            color = colorGray
                            true
                        }

                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            isPressed = false
                            color = colorTransparent
                            true
                        }

                        else -> false
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = "Undo or Redo icon",
            tint = Color.Black
        )
    }

    var delayInMillis = rememberSaveable {
        650L
    }
    LaunchedEffect(isPressed) {
        if (isPressed) {
            while (isPressed) {
                action()
                delay(delayInMillis)
                if (delayInMillis > 100) {
                    delayInMillis -= 200
                }
            }
            delayInMillis = 600L
        }
    }
}