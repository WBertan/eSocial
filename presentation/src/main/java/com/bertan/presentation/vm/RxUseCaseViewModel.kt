package com.bertan.presentation.vm

import androidx.lifecycle.*
import com.bertan.domain.interactor.RxUseCase
import com.bertan.presentation.state.ViewState

abstract class RxUseCaseViewModel<T>(private vararg val useCases: RxUseCase<*, *>) : ViewModel(), LifecycleObserver {
    protected fun postLoading() = postState(ViewState.Loading)
    protected fun T.postSuccess() = postState(ViewState.Success(this))
    protected fun Throwable?.postError(message: String) = postState(ViewState.Error(message, this))

    private val state: MutableLiveData<ViewState<T>> = MutableLiveData()

    fun getState(): LiveData<ViewState<T>> = state

    private fun postState(value: ViewState<T>) = state.postValue(value)

    abstract fun onCreateViewModel()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (state.value == null) {
            onCreateViewModel()
        }
    }

    override fun onCleared() {
        useCases.forEach { it.dispose() }
        super.onCleared()
    }
}