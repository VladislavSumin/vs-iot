package ru.vs.iot.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultComponentContext = defaultComponentContext()

        setContent {
            RootUi(defaultComponentContext)
        }
    }
}
