package com.imtuc.intellibite.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imtuc.intellibite.navigation.SetupNavGraph
import com.imtuc.intellibite.ui.theme.IntelliBiteTheme
import com.imtuc.intellibite.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            IntelliBiteTheme {
                navController = rememberNavController()

                SetupNavGraph(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    lifecycleOwner = this
                )
            }
        }
    }
}