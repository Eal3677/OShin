package com.suqi8.oshin.ui.mainscreen.softupdate

/**
 * Minimal data model representing a GitHub release used by the in-app update flow.
 *
 * The previous implementation that consumed the GitHub Releases API was removed from
 * the source tree, leaving the rest of the UI code referencing this type. To keep the
 * project compiling we expose a small data class that mirrors the few fields the UI
 * actually reads (currently only `name`).
 */
data class GitHubRelease(
    val name: String = "",
    val tagName: String = "",
    val htmlUrl: String = "",
    val publishedAt: String = "",
    val body: String = "",
    val assetName: String = "",
    val assetSize: Long = 0L,
    val downloadUrl: String = "",
    val isPrerelease: Boolean = false
)
