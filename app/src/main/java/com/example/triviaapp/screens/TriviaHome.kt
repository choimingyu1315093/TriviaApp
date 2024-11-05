package com.example.triviaapp.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp.components.Question

@Composable
fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel()){
    Question(viewModel)
}