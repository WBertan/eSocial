package com.bertan.esocial.di

import com.bertan.presentation.vm.*
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val presentationModule: Module = module {
    viewModel {
        AccountListViewModel(
            getAccountsUseCase = get()
        )
    }
    viewModel {
        AccountViewModel(
            getAccountUseCase = get(),
            addAccountUseCase = get()
        )
    }

    viewModel {
        CommentListViewModel(
            postId = getProperty("postId"),
            getCommentsByPostUseCase = get()
        )
    }
    viewModel {
        CommentViewModel(
            getCommentUseCase = get(),
            addCommentUseCase = get()
        )
    }

    viewModel {
        PostListViewModel(
            getPostsUseCase = get()
        )
    }
    viewModel {
        PostViewModel(
            getPostUseCase = get(),
            addPostUseCase = get()
        )
    }

    viewModel {
        SourceListViewModel(
            getSourcesUseCase = get()
        )
    }
}