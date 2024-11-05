package com.example.triviaapp.components

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.triviaapp.TAG
import com.example.triviaapp.screens.QuestionViewModel

@Composable
fun Question(viewModel: QuestionViewModel){
    val questions = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
        Log.d(TAG, "Question: Loading...")
    }else {
        questions?.forEach { questionItem ->
            Log.d(TAG, "Question: ${questionItem.question}")
        }
    }
}