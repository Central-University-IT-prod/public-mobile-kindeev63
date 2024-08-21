package com.knomster.mobile_kindeev63.presentation.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.presentation.entities.AccountScreenSate
import com.knomster.mobile_kindeev63.presentation.ui.elements.AdviceAndHolidays
import com.knomster.mobile_kindeev63.presentation.viewModels.AccountScreenViewModel
import com.knomster.mobile_kindeev63.presentation.viewModels.AccountScreenViewModelFactory
import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    mainViewModel: MainViewModel,
    onBackPressed: () -> Unit
) {
    BackHandler(onBack = onBackPressed)
    val viewModel: AccountScreenViewModel = viewModel(
        factory = AccountScreenViewModelFactory(mainViewModel = mainViewModel)
    )
    val screenSate by viewModel.accountScreenSate.observeAsState(initial = AccountScreenSate.SignIn)
    mainViewModel.account.observe(LocalLifecycleOwner.current) { viewModel.setAccountInformation(it) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopBar(back = onBackPressed)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (screenSate) {
                is AccountScreenSate.AccountInformation -> {
                    AccountInformation(viewModel = viewModel)
                }

                AccountScreenSate.SignIn -> {
                    SignInBox(
                        viewModel = viewModel,
                        onFail = {
                            scope.launch {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.invalid_user))
                            }
                        }
                    )
                }

                is AccountScreenSate.SignUp -> {
                    SignUpBox(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    back: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(elevation = 4.dp)
            .background(Color.White)
            .statusBarsPadding()
    ) {
        IconButton(onClick = back) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.Black,
                contentDescription = "Back arrow"
            )
        }
    }
}

@Composable
private fun AccountInformation(viewModel: AccountScreenViewModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val loginTextSize = screenWidth / 20
    val nameTextSize = screenWidth / 15
    val accountInformation = viewModel.getAccountInformation()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(10.dp),
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
                GlideImage(
                    imageModel = { accountInformation.photoUrl },
                    imageOptions = ImageOptions(contentScale = ContentScale.Inside),
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size((screenWidth / 2).dp),
                            color = Color.Black
                        )
                    },
                    success = { _, painter ->
                        Image(
                            modifier = Modifier
                                .size((screenWidth / 2).dp)
                                .clip(CircleShape),
                            painter = painter,
                            contentScale = ContentScale.Crop,
                            contentDescription = "User image",
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${accountInformation.firstName} ${accountInformation.lastName}",
                    fontSize = nameTextSize.sp,
                    lineHeight = nameTextSize.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = accountInformation.login,
                    fontSize = loginTextSize.sp,
                    lineHeight = loginTextSize.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(10.dp))
                ContactBox(text = accountInformation.email, iconResourceId = R.drawable.icon_email)
            }
        }
        AdviceAndHolidays(mainViewModel = viewModel.mainViewModel)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { viewModel.logOut() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(text = stringResource(id = R.string.log_out))
        }
    }
}

@Composable
private fun ContactBox(
    text: String,
    iconResourceId: Int
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
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = iconResourceId),
                    contentDescription = "Contact icon",
                    tint = Color.Black
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
private fun SignUpBox(viewModel: AccountScreenViewModel) {
    val accountScreenSate by viewModel.accountScreenSate.observeAsState(AccountScreenSate.SignUp())
    val randomUser = (accountScreenSate as AccountScreenSate.SignUp).randomUser
    if (randomUser == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Black
            )
        }
    } else {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val loginTextSize = screenWidth / 20
        val nameTextSize = screenWidth / 15
        var password by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                imageModel = { randomUser.photoUrl },
                imageOptions = ImageOptions(contentScale = ContentScale.Inside),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.size((screenWidth / 2).dp),
                        color = Color.Black
                    )
                },
                success = { _, painter ->
                    Image(
                        modifier = Modifier
                            .size((screenWidth / 2).dp)
                            .clip(CircleShape),
                        painter = painter,
                        contentScale = ContentScale.Crop,
                        contentDescription = "User image",
                    )
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = randomUser.login,
                fontSize = loginTextSize.sp,
                lineHeight = loginTextSize.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray
            )
            Text(
                text = "${randomUser.firstName} ${randomUser.lastName}",
                fontSize = nameTextSize.sp,
                lineHeight = nameTextSize.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            ContactBox(text = randomUser.email, iconResourceId = R.drawable.icon_email)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (password != "") {
                        viewModel.signUp(
                            randomUser = randomUser,
                            password = password
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(text = stringResource(id = R.string.sign_up))
            }
            TextButton(onClick = { viewModel.goToSignIn() }) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
private fun SignInBox(viewModel: AccountScreenViewModel, onFail: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = {
                Text(text = stringResource(id = R.string.login))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black
            )
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                viewModel.signIn(
                    login = login,
                    password = password,
                    onFail = onFail
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(text = stringResource(id = R.string.sign_in))
        }
        TextButton(
            onClick = { viewModel.goToSignUp() }
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                color = Color.Black
            )
        }
    }
}
