package com.melgarejojunior.kotlinrepos.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melgarejojunior.kotlinrepos.databinding.ActivityMainBinding
import com.melgarejojunior.kotlinrepos.structure.viewmanagement.onStateChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val itemsAdapter = RepoAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setupRecyclerView()
        }
        setContentView(binding.root)
        setObservers()
    }

    private fun ActivityMainBinding.setupRecyclerView() {
        val orientation = RecyclerView.VERTICAL
        with(shortenedUrlList) {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, orientation, false)
            addItemDecoration(DividerItemDecoration(this@MainActivity, orientation))
        }
    }

    private fun setObservers() {
        onStateChanged(viewModel) { state ->
            when (state) {
                MainViewState.Idle -> {}
                is MainViewState.NewPage -> {
                    itemsAdapter.submitData(lifecycle, state.page)
                }
            }
        }
    }
}