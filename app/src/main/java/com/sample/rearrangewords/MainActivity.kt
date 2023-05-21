package com.sample.rearrangewords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sample.rearrangewords.adapter.AnswerWordsAdapter
import com.sample.rearrangewords.adapter.RearrangeWordsAdapter
import com.sample.rearrangewords.databinding.ActivityMainBinding
import com.sample.rearrangewords.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()


    private val answerWordsAdapter by lazy {
        AnswerWordsAdapter { model ->
            viewModel.selectedAnswerWord(model)
            rearrangeWordsAdapter.setData(model)
        }
    }

    private val rearrangeWordsAdapter by lazy {
        RearrangeWordsAdapter {
            viewModel.selectedAnswerWord(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .mapNotNull { it.answerWords }
                    .collectLatest { answerWords ->
                        answerWordsAdapter.setData(answerWords)
                    }
            }
        }
    }

    private fun initUI() {
        with(binding) {
            val flexboxLayoutManager = FlexboxLayoutManager(this@MainActivity, FlexDirection.ROW)
            flexboxLayoutManager.justifyContent = JustifyContent.CENTER
            rvAnswerWords.layoutManager = flexboxLayoutManager
            rvAnswerWords.adapter = answerWordsAdapter

            val flexboxLayoutManager2 = FlexboxLayoutManager(this@MainActivity, FlexDirection.ROW)
            flexboxLayoutManager2.justifyContent = JustifyContent.CENTER
            rvRearrangeWords.layoutManager = flexboxLayoutManager2
            rvRearrangeWords.adapter = rearrangeWordsAdapter
        }
    }
}