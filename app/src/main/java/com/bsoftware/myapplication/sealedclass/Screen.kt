package com.bsoftware.myapplication.sealedclass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

sealed class Screen(val route : String?, val title : String?, val icon : ImageVector?) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)

    object Panic : Screen("panic", "panic", null)

}