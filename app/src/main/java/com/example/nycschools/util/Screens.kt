package com.example.nycschools.util

sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
    object Detail: Screens("Detail_screen")
}