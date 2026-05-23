package com.rodrigoloq.moviepedia.account

import android.content.Intent
import android.content.res.Configuration
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.sqlite.db.SupportSQLiteOpenHelper
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.account.viewmodel.EditInformationViewModel
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.User
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme
import kotlin.text.trim

@Preview(showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EditInformationViewPreview(){
    MoviepediaTheme{
        EditInformationView(navController = rememberNavController())
    }
}

@Composable
fun EditInformationView(navController: NavController,
                        modifier: Modifier = Modifier,
                        viewModel: EditInformationViewModel = viewModel()){

    val context = LocalContext.current
    val focus = LocalFocusManager.current
    val user = viewModel.userData

    LaunchedEffect(Unit) {
        viewModel.getUserInfo(MoviepediaApp.auth.id)
    }

    var nameTextValue by remember(user) { mutableStateOf(user?.username ?: "") }
    var emailTextValue by remember(user) { mutableStateOf(user?.email ?: "") }
    var imageValue by remember(user) { mutableStateOf(user?.imgLocation ?: "") }

    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    var blankNameError by remember { mutableStateOf(false) }
    var blankEmailError by remember { mutableStateOf(false) }
    var wrongEmailFormatError by remember { mutableStateOf(false) }

    val editInformation = {
        focus.clearFocus()
        if (blankEmailError || wrongEmailFormatError){
            emailFocusRequester.requestFocus()
        } else if(blankNameError){
            nameFocusRequester.requestFocus()
        } else{
            viewModel.editUsernameAndEmail(MoviepediaApp.auth.id,
                nameTextValue, emailTextValue, imageValue){ result ->
                if (result) {
                    Toast.makeText(context,
                        "Sus datos se han actualizado correctamente",
                        Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                } else {
                    Toast.makeText(context,
                        "Error al actualizar sus datos",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    val galleryResult = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()) { uri ->
        if(uri != null){
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flag)
            imageValue = uri.toString()
        }
    }

    Scaffold() {
        Column(modifier.padding(it)
            .background(Color.Transparent)
            .fillMaxSize()) {
            Box() {
                Icon(painterResource(R.drawable.ic_back_arrow),
                    "",
                    Modifier.padding(start = 4.dp).size(25.dp).clickable{
                        navController.popBackStack()
                    })
                Text("Editar informacion", modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,)
            }
            HorizontalDivider()
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(10.dp)){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageValue)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.perfil_user),
                    error = painterResource(R.drawable.perfil_user),
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(120.dp)
                )
                Image(
                    painter = painterResource(R.drawable.icono_editar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .align(Alignment.TopEnd)
                        .clickable{
                            galleryResult.launch(PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            ))
                        }
                )
            }
            OutlinedTextField(modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .focusRequester(nameFocusRequester),
                singleLine = true,
                value = nameTextValue,
                onValueChange = {
                    blankNameError = it.trim().isEmpty()
                    nameTextValue = it },
                supportingText = {
                    if(blankNameError)
                        Text(text = "Ingrese nombres",
                            color = MaterialTheme.colorScheme.error)
                },
                isError = blankNameError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = {
                    Text("Nombres")
                })
            OutlinedTextField(modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .focusRequester(emailFocusRequester),
                singleLine = true,
                value = emailTextValue,
                onValueChange = {
                    blankEmailError = it.trim().isEmpty()
                    wrongEmailFormatError = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    emailTextValue = it },
                supportingText = {
                    if(blankEmailError)
                        Text(text = "Ingrese un correo",
                            color = MaterialTheme.colorScheme.error)
                    else if(wrongEmailFormatError)
                        Text(text = "Formato invalido",
                            color = MaterialTheme.colorScheme.error)
                },
                isError = blankEmailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = {
                    Text("Email")
                })
            Row(Modifier.fillMaxHeight()) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Bottom)
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    onClick = {
                        editInformation()
                    },
                    shape = RoundedCornerShape(8.dp)) {
                    Text(text = "ACTUALIZAR")
                }
            }
        }
    }
}