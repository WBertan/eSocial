package com.bertan.esocial.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bertan.esocial.R
import com.bertan.esocial.ui.adapter.SourceViewAdapter
import com.bertan.presentation.model.SourceView
import com.bertan.presentation.state.ViewState
import com.bertan.presentation.vm.SourceListViewModel
import kotlinx.android.synthetic.main.activity_onboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardActivity : AppCompatActivity() {
    private val viewModel: SourceListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)
        lifecycle.addObserver(viewModel)
        viewModel.getState().observe(this, Observer { it?.let(::handleState) })
    }

    private fun handleState(viewState: ViewState<List<SourceView>>) =
            when (viewState) {
                is ViewState.Loading ->
                    Toast.makeText(this, "Loading!", Toast.LENGTH_SHORT).show()
                is ViewState.Success -> {
                    sources.adapter = SourceViewAdapter(viewState.data)
                }
                is ViewState.Error ->
                    Toast.makeText(this, "Error: ${viewState.message}", Toast.LENGTH_SHORT).show()
            }
}