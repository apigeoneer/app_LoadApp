package com.gmail.apigeoneer.loadapp

fun ButtonText(buttonState: ButtonState): String {
   return when(buttonState) {
       ButtonState.Clicked -> "Clicked"
       ButtonState.Loading -> "Downloading..."
       ButtonState.Completed -> "Downloaded!"
       else -> "DOWNLOAD"
   }
}