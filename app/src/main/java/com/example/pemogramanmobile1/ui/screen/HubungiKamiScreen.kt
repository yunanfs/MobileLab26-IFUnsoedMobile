package com.example.pemogramanmobile1.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pemogramanmobile1.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubungiKamiScreen(
    navController: NavController
) {

    // ==============================
    // STATE
    // ==============================

    var emailText by remember {
        mutableStateOf("")
    }

    var messageText by remember {
        mutableStateOf("")
    }

    var problemType by rememberSaveable {
        mutableStateOf("Pilih Tipe Pesan")
    }

    var isAgreed by rememberSaveable {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // ==============================
    // VALIDASI
    // ==============================

    val isEmailValid =
        emailText.contains("@") &&
                emailText.isNotBlank()

    val isMessageValid =
        messageText.length >= 10

    val isFormValid =
        isEmailValid &&
                isMessageValid &&
                isAgreed &&
                problemType != "Pilih Tipe Pesan"

    // ==============================
    // SNACKBAR
    // ==============================

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    // ==============================
    // SCAFFOLD
    // ==============================

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hubungi Kami"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.back_icon
                            ),
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->

        StatelessFormHubungiKami(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),

            email = emailText,

            onEmailChange = {
                emailText = it
            },

            message = messageText,

            onMessageChange = {
                messageText = it
            },

            problemType = problemType,

            onProblemTypeChange = {
                problemType = it
            },

            isAgreed = isAgreed,

            onAgreedChange = {
                isAgreed = it
            },

            imageUri = imageUri,

            onImagePicked = {
                imageUri = it
            },

            isEmailValid = isEmailValid,

            isFormValid = isFormValid,

            onSubmit = {

                scope.launch {

                    snackbarHostState.showSnackbar(
                        message = "Pesan Terkirim"
                    )

                    delay(1000)
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessFormHubungiKami(
    modifier: Modifier = Modifier,

    email: String,
    onEmailChange: (String) -> Unit,

    message: String,
    onMessageChange: (String) -> Unit,

    problemType: String,
    onProblemTypeChange: (String) -> Unit,

    isAgreed: Boolean,
    onAgreedChange: (Boolean) -> Unit,

    imageUri: Uri?,
    onImagePicked: (Uri?) -> Unit,

    isEmailValid: Boolean,
    isFormValid: Boolean,

    onSubmit: () -> Unit
) {

    // ==============================
    // DROPDOWN STATE
    // ==============================

    var expanded by remember {
        mutableStateOf(false)
    }

    val options = listOf(
        "Pertanyaan",
        "Keluhan",
        "Saran"
    )

    // ==============================
    // PHOTO PICKER
    // ==============================

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            onImagePicked(uri)
        }

    // ==============================
    // FORM
    // ==============================

    Column(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                vertical = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hubungi Kami",
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==============================
        // EMAIL
        // ==============================

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = {
                Text("Email Anda")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        id = R.drawable.mail_icon
                    ),
                    contentDescription = "Email"
                )
            },
            isError = email.isNotEmpty() && !isEmailValid,
            supportingText = {
                if (email.isNotEmpty() && !isEmailValid) {
                    Text(
                        text = "Format Email Salah"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==============================
        // DROPDOWN TIPE PESAN
        // ==============================

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = problemType,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Tipe Pesan")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                options.forEach { option ->

                    DropdownMenuItem(
                        text = {
                            Text(option)
                        },
                        onClick = {

                            onProblemTypeChange(
                                option
                            )

                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==============================
        // PESAN
        // ==============================

        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            label = {
                Text("Pesan")
            },
            isError = message.isNotEmpty() &&
                    message.length < 10,
            supportingText = {

                if (
                    message.isNotEmpty() &&
                    message.length < 10
                ) {
                    Text(
                        text = "Pesan minimal 10 karakter"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==============================
        // PILIH GAMBAR
        // ==============================

        Button(
            onClick = {

                photoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts
                            .PickVisualMedia
                            .ImageOnly
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Pilih Gambar"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // ==============================
        // INFORMASI GAMBAR
        // ==============================

        if (imageUri != null) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Gambar dipilih:"
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = imageUri
                            ?.lastPathSegment
                            ?: "Gambar"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // ==============================
        // CHECKBOX
        // ==============================

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isAgreed,
                onCheckedChange = onAgreedChange
            )

            Text(
                text = "Saya menyetujui syarat dan ketentuan"
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==============================
        // SUBMIT
        // ==============================

        Button(
            onClick = onSubmit,
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                vertical = 12.dp
            )
        ) {

            Icon(
                painter = painterResource(
                    id = R.drawable.send_icon
                ),
                contentDescription = "Send"
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = "Kirim"
            )
        }
    }
}