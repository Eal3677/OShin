package com.suqi8.oshin.ui.mainscreen.softupdate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Stub view model for the (currently disabled) self-update flow.
 *
 * The full update implementation is not part of the open-source tree yet. The rest of
 * the UI still references this view model, so we provide a minimal Hilt-injectable
 * implementation that always reports "no update available" and ignores manual download
 * requests. This is enough to satisfy the compiler and lets the in-app update dialog
 * stay dormant until a real implementation lands.
 */
@HiltViewModel
class UpdateViewModel @Inject constructor() : ViewModel() {

    private val _updateCheckResult = MutableStateFlow<GitHubRelease?>(null)
    val updateCheckResult: StateFlow<GitHubRelease?> = _updateCheckResult.asStateFlow()

    private var autoDownloadFlag: Boolean = false

    /**
     * Performs an automatic update check. The current implementation is a no-op and
     * leaves [updateCheckResult] as `null`, effectively reporting "no update".
     */
    fun autoCheckForUpdate(currentVersion: String) {
        // No remote check is performed. Subclasses can override this behaviour once a
        // real update mechanism is reintroduced.
    }

    /**
     * Clears any previously surfaced update result so the dialog can be dismissed.
     */
    fun clearUpdateCheckResult() {
        _updateCheckResult.value = null
    }

    /**
     * Marks the next navigation to the update screen as an automatic download. Kept
     * for compatibility with [UpdateAvailableDialog].
     */
    fun setAutoDownloadFlag() {
        autoDownloadFlag = true
    }

    /**
     * Returns and clears the auto-download flag.
     */
    fun consumeAutoDownloadFlag(): Boolean {
        val flag = autoDownloadFlag
        autoDownloadFlag = false
        return flag
    }
}
