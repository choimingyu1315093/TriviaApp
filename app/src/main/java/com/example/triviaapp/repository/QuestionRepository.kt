package com.example.triviaapp.repository

import android.util.Log
import com.example.triviaapp.data.DataOrException
import com.example.triviaapp.model.Question
import com.example.triviaapp.model.QuestionItem
import com.example.triviaapp.network.QuestionApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//Repository는 데이터 접근을 더 간단하고 구조화된 방식으로 관리하기 위해 사용하는 패턴이다.
//여기서 말하는 데이터 접근은 RoomDatabase, Retrofit 등 라이브러리를 사용해서 데이터를 가져오거나 저장하는 작업을 의미한다.
class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception>{
        try {
            dataOrException.loading = true
            dataOrException.data = questionApi.getAllQuestions()
            if(dataOrException.data.toString().isNotEmpty()){
                dataOrException.loading = false
            }
        }catch (exception: Exception){
            dataOrException.e = exception
            Log.d("TAG", "getAllQuestions: e ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }
}

