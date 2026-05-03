package com.suqi8.oshin.ui.mainscreen.softupdate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class UpdateViewModel @Inject constructor() : ViewModel() {
    private val _updateCheckResult = MutableStateFlow<GitHubRelease?>(null)
    val updateCheckResult: StateFlow<GitHubRelease?> = _updateCheckResult.asStateFlow()

    fun autoCheckForUpdate(currentVersion: String) {
    }

    fun clearUpdateCheckResult() {
        _updateCheckResult.value = null
    }

    fun setAutoDownloadFlag() {
    }
}
