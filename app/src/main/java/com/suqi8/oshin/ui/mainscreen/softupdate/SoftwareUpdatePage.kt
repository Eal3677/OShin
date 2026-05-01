package com.suqi8.oshin.ui.mainscreen.softupdate

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import top.yukonga.miuix.kmp.basic.ScrollBehavior

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Suppress("UNUSED_PARAMETER")
fun SoftwareUpdatePage(
    navController: NavController,
    topAppBarScrollBehavior: ScrollBehavior,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BasicText(text = "Software update page")
    }
}
