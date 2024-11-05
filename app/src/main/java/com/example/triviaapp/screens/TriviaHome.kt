package com.example.triviaapp.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp.components.Question
import com.example.triviaapp.components.QuestionDisplay

@Composable
fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel()){
    Question(viewModel)
}