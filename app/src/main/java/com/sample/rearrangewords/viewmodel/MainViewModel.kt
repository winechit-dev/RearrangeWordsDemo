package com.sample.rearrangewords.viewmodel

import androidx.lifecycle.ViewModel
import com.sample.rearrangewords.datasoruce.DataSource
import com.sample.rearrangewords.model.WordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(answerWords = DataSource.answerWords) }
    }

    fun selectedAnswerWord(model: WordModel) {
        _uiState.update { state->
            val mutableList = state.answerWords.orEmpty().toMutableList()
            val index = mutableList.indexOf(model)
            mutableList[index] = model.copy(isSelected = !model.isSelected)
            state.copy(answerWords = mutableList)
        }
    }

    data class UiState(
        val answerWords: List<WordModel>? = null,
        val rearrangeWords: List<WordModel>? = null,
    )
}