package com.roadhelp.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.roadhelp.app.core.design.RoadHelpTheme
import com.roadhelp.app.presentation.home.HomeScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RoadHelpTheme { HomeScreen() } }
    }
}
