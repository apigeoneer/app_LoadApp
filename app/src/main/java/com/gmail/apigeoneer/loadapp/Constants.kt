package com.gmail.apigeoneer.loadapp

fun idToURL(id: Int): String {
    return when(id) {
        0 -> "https://github.com/bumptech/glide.git"
        1 -> "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter.git"
        2 -> "https://github.com/square/retrofit.git"
        else -> "https://github.com/bumptech/glide.git"
    }
}

fun idToRepoName(id: Int): String {
    return when(id) {
        0 -> "Glide repository"
        1 -> "Load app repository"
        2 -> "Retrofit repository"
        else -> "Glide repository"
    }
}