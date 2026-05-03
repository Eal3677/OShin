package com.suqi8.oshin.ui.mainscreen.softupdate

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.suqi8.oshin.R
import com.suqi8.oshin.ui.activity.components.FunPage
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.Text

/**
 * Placeholder software update page.
 *
 * The full implementation that talks to GitHub Releases is not part of this branch.
 * This stub keeps the navigation entry alive and informs the user that the in-app
 * update flow is currently unavailable, instead of breaking compilation.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SoftwareUpdatePage(
    navController: NavController,
    topAppBarScrollBehavior: ScrollBehavior,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    FunPage(
        title = stringResource(R.string.update_page_software_version),
        navController = navController,
        scrollBehavior = topAppBarScrollBehavior,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        animationKey = "software_update"
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.update_page_status_latest))
        }
    }
}
