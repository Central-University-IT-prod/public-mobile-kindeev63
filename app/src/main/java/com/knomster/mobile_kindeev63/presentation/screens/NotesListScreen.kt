package com.knomster.mobile_kindeev63.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel
import com.knomster.mobile_kindeev63.presentation.ui.elements.NoteItem
import com.knomster.mobile_kindeev63.presentation.viewModels.NotesListScreenViewModel
import com.knomster.mobile_kindeev63.presentation.viewModels.NotesListScreenViewModelFactory
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NotesListScreen(
    mainViewModel: MainViewModel,
    navigateToNoteEditScreen: (Int) -> Unit,
    navigateToAccountScreen: () -> Unit
) {
    val viewModel: NotesListScreenViewModel = viewModel(
        factory = NotesListScreenViewModelFactory(mainViewModel = mainViewModel)
    )
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    mainViewModel.allNotes.observe(LocalLifecycleOwner.current) { viewModel.setAllNotes(it) }
    val notes by viewModel.notes.observeAsState(emptyList())
    val searchText by viewModel.searchText.observeAsState()
    Scaffold(
        topBar = {
            NotesListScreenTopBar(
                viewModel = viewModel,
                navigateToAccountScreen = navigateToAccountScreen
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color.Black,
                onClick = {
                    viewModel.makeNoteAnd { it?.let(navigateToNoteEditScreen) }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    tint = Color.White,
                    contentDescription = "Add note"
                )
            }
        }
    ) { paddingValues ->
        if (notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_notes)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                items(
                    items = notes.filter { it.note.title.contains(searchText ?: "") },
                    key = { it.note.id }
                ) { noteWithSelected ->

                    NoteItem(
                        title = if (noteWithSelected.note.title.length > 20) {
                            noteWithSelected.note.title.substring(0, 20) + "..."
                        } else {
                            noteWithSelected.note.title
                        },
                        date = if (noteWithSelected.note.date == null) {
                            null
                        } else {
                            dateFormatter.format(noteWithSelected.note.date)
                        },
                        selected = noteWithSelected.selected,
                        onClick = {
                            if (notes.any { it.selected }) {
                                viewModel.changeSelectionStateOfNote(noteWithSelected.note.id)
                            } else {
                                viewModel.search(null)
                                navigateToNoteEditScreen(noteWithSelected.note.id)
                            }
                        },
                        onLongClick = {
                            viewModel.changeSelectionStateOfNote(noteWithSelected.note.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotesListScreenTopBar(
    viewModel: NotesListScreenViewModel,
    navigateToAccountScreen: () -> Unit
) {
    val notes by viewModel.notes.observeAsState(emptyList())
    val searchText by viewModel.searchText.observeAsState()
    if (notes.any { it.selected }) {
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
            IconButton(onClick = { viewModel.clearSelectedNotes() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.Black,
                    contentDescription = "Back arrow"
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (notes.isNotEmpty()) {
                    SearchItem(value = searchText, onValueChange = { viewModel.search(it) })
                }
            }
            IconButton(
                onClick = {
                    viewModel.search(null)
                    viewModel.deleteSelectionNotes()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    tint = Color.Black,
                    contentDescription = "Delete selected notes"
                )
            }
        }
    } else {
        val account by viewModel.mainViewModel.account.observeAsState()
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
            account?.firstName?.let {
                if (searchText == null) {
                    Text(
                        text = stringResource(id = R.string.hello_text) + " $it"
                    )
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (notes.isNotEmpty()) {
                    SearchItem(value = searchText, onValueChange = { viewModel.search(it) })
                }
            }
            GlideImage(
                imageModel = { account?.photoUrl },
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
                            .clickable(onClick = navigateToAccountScreen),
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
                            .clickable(onClick = navigateToAccountScreen),
                        painter = painterResource(id = R.drawable.ic_account_circle),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Account icon",
                    )
                }
            )
        }
    }
}

@Composable
fun SearchItem(
    value: String?,
    onValueChange: (String?) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    if (value == null) {
        IconButton(
            onClick = {
                onValueChange("")
            }
        ) {
            Icon(Icons.Filled.Search, contentDescription = "Search")
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlaceholderTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                },
                singleLine = true,
                hintText = stringResource(id = R.string.search),
                modifier = Modifier.weight(1f),
                textFieldModifier = Modifier.focusRequester(focusRequester)
            )
            IconButton(
                modifier = Modifier.alpha(0.5f),
                onClick = {
                    if (value == "") {
                        onValueChange(null)
                    } else {
                        onValueChange("")
                    }

                }
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close search")
            }
        }
    }
    LaunchedEffect(value == null) {
        if (value != null) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
fun PlaceholderTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hintText: String = "",
    singleLine: Boolean = false,
    fontSize: TextUnit = 18.sp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = textFieldModifier.fillMaxWidth(),
            singleLine = singleLine,
            textStyle = TextStyle.Default.copy(fontSize = fontSize)
        )
        if (value.isEmpty()) {
            Text(
                text = hintText,
                style = TextStyle(color = Color.Gray),
                fontSize = fontSize,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}