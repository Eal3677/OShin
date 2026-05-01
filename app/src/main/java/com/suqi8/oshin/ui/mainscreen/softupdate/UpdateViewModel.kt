package com.suqi8.oshin.ui.mainscreen.softupdate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor() : ViewModel() {
    private val _updateCheckResult = MutableStateFlow<GitHubRelease?>(null)
    val updateCheckResult = _updateCheckResult.asStateFlow()

    fun autoCheckForUpdate(currentVersion: String) {
        if (currentVersion.isBlank()) return
        _updateCheckResult.value = null
    }

    fun setAutoDownloadFlag() {
    }

    fun clearUpdateCheckResult() {
        _updateCheckResult.value = null
    }
}
