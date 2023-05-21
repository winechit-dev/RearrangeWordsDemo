package com.sample.rearrangewords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sample.rearrangewords.adapter.AnswerWordsAdapter
import com.sample.rearrangewords.adapter.RearrangeWordsAdapter
import com.sample.rearrangewords.databinding.ActivityMainBinding
import com.sample.rearrangewords.model.WordModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //you must add the Google Maven repository to your project
    private val answerWords = listOf(
        WordModel(
            id = "1",
            word = "must",
            isSelected = false
        ),
        WordModel(
            id = "2",
            word = "to",
            isSelected = false
        ),
        WordModel(
            id = "3",
            word = "Google",
            isSelected = false
        ),
        WordModel(
            id = "4",
            word = "you",
            isSelected = false
        ),
        WordModel(
            id = "5",
            word = "repository",
            isSelected = false
        ),
        WordModel(
            id = "6",
            word = "add",
            isSelected = false
        ),
        WordModel(
            id = "7",
            word = "project",
            isSelected = false
        ),
        WordModel(
            id = "8",
            word = "the",
            isSelected = false
        ),
        WordModel(
            id = "9",
            word = "Maven",
            isSelected = false
        ),
        WordModel(
            id = "10",
            word = "your",
            isSelected = false
        )
    )


    private val answerWordsAdapter by lazy {
        AnswerWordsAdapter { model ->
            rearrangeWordsAdapter.setData(model)
        }
    }

    private val rearrangeWordsAdapter by lazy {
        RearrangeWordsAdapter {
            selectedWord(it)
        }
    }

    private fun selectedWord(it: WordModel) {
        answerWordsAdapter.selectedWord(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            val flexboxLayoutManager = FlexboxLayoutManager(this@MainActivity, FlexDirection.ROW)
            flexboxLayoutManager.justifyContent = JustifyContent.CENTER
            rvAnswerWords.layoutManager = flexboxLayoutManager
            rvAnswerWords.adapter = answerWordsAdapter
            answerWordsAdapter.setData(answerWords)

            val flexboxLayoutManager2 = FlexboxLayoutManager(this@MainActivity, FlexDirection.ROW)
            flexboxLayoutManager2.justifyContent = JustifyContent.CENTER
            rvRearrangeWords.layoutManager = flexboxLayoutManager2
            rvRearrangeWords.adapter = rearrangeWordsAdapter
        }
    }
}