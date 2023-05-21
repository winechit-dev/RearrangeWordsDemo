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
        _uiState.update { state ->
            val newAnswerWords = state.answerWords.toMutableList()
            val newModel = newAnswerWords.find { it.id == model.id }
            val index = newAnswerWords.indexOf(newModel)
            newAnswerWords[index] = model.copy(isSelected = true)
            state.copy(answerWords = newAnswerWords)
        }
    }

    fun unselectedAnswerWord(model: WordModel) {
        _uiState.update { state ->
            val newAnswerWords = state.answerWords.toMutableList()
            val newModel = newAnswerWords.find { it.id == model.id }
            val index = newAnswerWords.indexOf(newModel)
            newAnswerWords[index] = model.copy(isSelected = false)
            state.copy(answerWords = newAnswerWords)
        }
    }

    fun addRearrangeWord(model: WordModel) {
        _uiState.update { state ->
            val mutableList = state.rearrangeWords.toMutableList()
            mutableList.add(model)
            state.copy(rearrangeWords = mutableList)
        }
    }

    fun removeRearrangeWord(model: WordModel) {
        _uiState.update { state ->
            val mutableList = state.rearrangeWords.toMutableList()
            mutableList.remove(model)
            state.copy(rearrangeWords = mutableList)
        }
    }

    data class UiState(
        val answerWords: List<WordModel> = emptyList(),
        val rearrangeWords: List<WordModel> = emptyList(),
    )
}