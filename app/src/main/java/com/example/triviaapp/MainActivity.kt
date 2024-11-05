package com.example.triviaapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp.components.Question
import com.example.triviaapp.screens.QuestionViewModel
import com.example.triviaapp.screens.TriviaHome
import com.example.triviaapp.ui.theme.TriviaAppTheme
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaAppTheme {
                Surface (
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .statusBarsPadding()
                        .safeDrawingPadding()
                ){
                    TriviaHome()
                }
            }
        }
    }
}

@Preview
@Composable
fun TriviaHomePreview(){
    TriviaAppTheme {
        Surface (
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .statusBarsPadding()
                .safeDrawingPadding()
        ){
            TriviaHome()
        }
    }
}