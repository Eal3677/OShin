package com.suqi8.oshin.ui.mainscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.highcapable.yukihookapi.hook.factory.prefs
import com.suqi8.oshin.BuildConfig
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.extra.SuperDialog

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("LocalContextConfigurationRead")
@Composable
fun VerifyDialog() {
    val context = LocalContext.current

    val fullVersion = BuildConfig.VERSION_NAME
    val currentVersionPrefix = fullVersion.split(".")[0] + "." + fullVersion.split(".")[1]

    val show = remember {
        mutableStateOf((context.prefs("settings").getString("verifyVersion", "0") != currentVersionPrefix) && context.resources.configuration.locales[0].language.endsWith("zh"))
    }
    var inputText by remember { mutableStateOf("") }

    val onVerificationSuccess = {
        Toast.makeText(context, "Verification successful!", Toast.LENGTH_SHORT).show()
        context.prefs("settings").edit()
            .putString("verifyVersion", currentVersionPrefix).apply()
        show.value = false
    }

    if (show.value && !BuildConfig.DEBUG) {
        SuperDialog(
            show = show,
            summary = "To respect the developer's work, please enter the module download link for verification. This module is only published on GitHub. If you downloaded it from paid third-party drives, please report the uploader."
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextVerificationContent(
                    text = inputText,
                    onTextChange = { inputText = it },
                    onVerify = {
                        when {
                            inputText.contains("github.com/suqi8/OShin", ignoreCase = true) -> onVerificationSuccess()
                            inputText.contains("github.com/Xposed-Modules-Repo/com.suqi8.oshin", ignoreCase = true) -> onVerificationSuccess()
                            else -> Toast.makeText(context, "Invalid input. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TextVerificationContent(
    text: String,
    onTextChange: (String) -> Unit,
    onVerify: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            label = "GitHub URL",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onVerify,
            colors = ButtonDefaults.textButtonColorsPrimary(),
            text = "Verify"
        )
    }
}
