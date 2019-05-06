package com.bertan.presentation.vm

import com.bertan.domain.interactor.source.GetSources
import com.bertan.presentation.mapper.SourceMapper.asSourceView
import com.bertan.presentation.model.SourceView

class SourceListViewModel(private val getSourcesUseCase: GetSources) :
    RxUseCaseViewModel<List<SourceView>>(getSourcesUseCase) {
    override fun onCreateViewModel() {
        postLoading()

        getSourcesUseCase.execute(
            onNext = { sources -> sources.map { it.asSourceView }.postSuccess() },
            onError = { it.postError("Failed to load Sources!") }
        )
    }
}