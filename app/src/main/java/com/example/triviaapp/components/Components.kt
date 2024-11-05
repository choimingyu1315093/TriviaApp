package com.example.triviaapp.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.color
import com.example.triviaapp.TAG
import com.example.triviaapp.model.QuestionItem
import com.example.triviaapp.screens.QuestionViewModel
import com.example.triviaapp.util.AppColors
import kotlin.coroutines.coroutineContext

@Composable
fun Question(viewModel: QuestionViewModel){
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember{
        mutableStateOf(0)
    }


    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
        Log.d(TAG, "Question: Loading...")
    }else {
        val question = try {
            questions?.get(questionIndex.value)
        }catch (ex: Exception){
            null
        }

        if(questions != null){
            QuestionDisplay(
                question = question!!,
                questionIndex = questionIndex,
                viewModel = viewModel,
                onNextClicked = {
                    questionIndex.value = questionIndex.value + 1
                }
            )
        }
    }
}

@Composable
fun QuestionDisplay(
    question:QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionViewModel,
    onNextClicked: (Int) -> Unit = {},
    modifier: Modifier = Modifier
){
    val choicesState = remember (question){
        question.choices.toMutableList()
    }

    val answerState = remember{
        mutableStateOf<Int?>(null)
    }

    val collectAnswerState = remember(question){
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(question){
        {
            answerState.value = it
            collectAnswerState.value = choicesState[it] == question.answer
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        color = AppColors.mDarkPurple
    ) {
        Column (
            modifier = modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){
            if(questionIndex.value >= 3){
                ShowProgress(score = questionIndex.value)
            }
            QuestionTracker(
                counter = questionIndex.value,
                outOff = viewModel.getTotalQuestionCount()
            )
            DrawDottedLine(PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
            Column {
                Text(
                    text = question.question,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = AppColors.mOffWhite,
                    modifier = modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f)
                )

                choicesState.forEachIndexed { index, answerText ->
                    Row (
                        modifier = modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(4.dp, Brush.linearGradient(
                                colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple)
                            ), shape = RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(topStartPercent = 50, topEndPercent = 50, bottomStartPercent = 50, bottomEndPercent = 50))
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RadioButton(
                            selected = answerState.value == index,
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = modifier
                                .padding(16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if(collectAnswerState.value == true && index == answerState.value){
                                    Color.Green.copy(alpha = 0.2f)
                                }else {
                                    Color.Red.copy(alpha = 0.2f)
                                }
                            )
                        )
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if(collectAnswerState.value == true && index == answerState.value){
                                        Color.Green
                                    }else if(collectAnswerState.value == false && index == answerState.value){
                                        Color.Red
                                    }else {
                                        AppColors.mOffWhite
                                    },
                                    fontSize = 17.sp
                                )
                            ){
                                append(answerText)
                            }
                        }
                        Text(
                            text = annotatedString,
                            modifier = modifier.padding(6.dp)
                        )
                    }
                }
                Button(
                    onClick = {
                        onNextClicked(questionIndex.value)
                    },
                    modifier = modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.mLightBlue
                    )
                ) {
                    Text(
                        text = "NEXT",
                        modifier = modifier.padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionTracker(
    counter: Int = 10,
    outOff: Int = 100,
    modifier: Modifier = Modifier
){
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
                withStyle(style = SpanStyle(color = AppColors.mLightGray, fontWeight = FontWeight.Bold, fontSize = 27.sp)){
                    append("Question $counter/")

                }
                withStyle(style = SpanStyle(color = AppColors.mLightGray, fontWeight = FontWeight.Light, fontSize = 14.sp)){
                    append("$outOff")
                }
            }
        },
        modifier = modifier.padding(20.dp)
    )
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect, modifier: Modifier = Modifier){
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp),
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, y = 0f),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun ShowProgress(score: Int = 12, modifier: Modifier = Modifier){
    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075), Color(0xFFBE6BE5)))
    val progressFactor by remember (score){
        mutableStateOf(score*0.005f)
    }

    Row (
        modifier = modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(AppColors.mLightPurple, AppColors.mLightPurple)
                ),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            ))
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {

            },
            modifier = modifier
                .fillMaxWidth(progressFactor)
                .background(
                    brush = gradient
                ),
            enabled = false,
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(
                text = (score*10).toString(),
                modifier = modifier
                    .clip(RoundedCornerShape(23.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.87f)
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}