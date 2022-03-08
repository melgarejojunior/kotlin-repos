package com.melgarejojunior.kotlinrepos.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melgarejojunior.kotlinrepos.R
import com.melgarejojunior.kotlinrepos.databinding.ActivityMainBinding
import com.melgarejojunior.kotlinrepos.presentation.main.adapter.RepoAdapter
import com.melgarejojunior.kotlinrepos.presentation.main.viewmodel.MainViewModel
import com.melgarejojunior.kotlinrepos.presentation.main.viewmodel.MainViewState
import com.melgarejojunior.kotlinrepos.structure.viewmanagement.onStateChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
                MainViewState.Idle -> hideLoading()
                MainViewState.Loading -> showLoading()
                is MainViewState.Result -> handleResult(state)
                is MainViewState.Error -> handleErrorState(state)
            }
        }
    }

    private fun handleResult(state: MainViewState.Result) {
        hideLoading()
        itemsAdapter.submitData(lifecycle, state.page)
    }

    private fun showLoading() {
        binding.loadingView.root.isVisible = true
    }

    private fun hideLoading() {
        binding.loadingView.root.isVisible = false
    }

    private fun handleErrorState(state: MainViewState.Error) {
        hideLoading()
        AlertDialog.Builder(this)
            .setMessage(state.errorMessageRes)
            .setTitle(R.string.error_title)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .create()
            .show()
    }
}