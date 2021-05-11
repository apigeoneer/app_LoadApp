package com.gmail.apigeoneer.loadapp

// https://blog.kotlin-academy.com/enum-vs-sealed-class-which-one-to-choose-dc92ce7a4df5
sealed class ButtonState {
    object Clicked: ButtonState()
    object Loading: ButtonState()
    object Completed: ButtonState()
}