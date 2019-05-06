package com.bertan.presentation.vm

import com.bertan.domain.interactor.source.GetSources
import com.bertan.presentation.mapper.SourceMapper.asSourceView
import com.bertan.presentation.model.SourceView

class SourceListViewModel(private val getSources: GetSources) : RxUseCaseViewModel<List<SourceView>>(getSources) {
    override fun onCreateViewModel() {
        postLoading()

        getSources.execute(
            onNext = { sources -> sources.map { it.asSourceView }.postSuccess() },
            onError = { it.postError("Failed to load Sources!") }
        )
    }
}