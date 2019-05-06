package com.bertan.esocial.di

import com.bertan.domain.interactor.account.AddAccount
import com.bertan.domain.interactor.account.GetAccount
import com.bertan.domain.interactor.account.GetAccounts
import com.bertan.domain.interactor.comment.AddComment
import com.bertan.domain.interactor.comment.GetComment
import com.bertan.domain.interactor.comment.GetCommentsByPost
import com.bertan.domain.interactor.post.AddPost
import com.bertan.domain.interactor.post.GetPost
import com.bertan.domain.interactor.post.GetPosts
import com.bertan.domain.interactor.source.GetSources
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val domainModule: Module = module {
    single {
        AddAccount(
            repository = get(),
            executor = get()
        )
    }
    single {
        GetAccount(
            repository = get(),
            executor = get()
        )
    }
    single {
        GetAccounts(
            repository = get(),
            executor = get()
        )
    }

    single {
        AddComment(
            repository = get(),
            executor = get()
        )
    }
    single {
        GetComment(
            repository = get(),
            executor = get()
        )
    }
    single {
        GetCommentsByPost(
            repository = get(),
            executor = get()
        )
    }

    single {
        AddPost(
            repository = get(),
            executor = get()
        )
    }
    single {
        GetPost(
            repository = get(),
            executor = get()
        )
    }
    single {
        GetPosts(
            repository = get(),
            executor = get()
        )
    }

    single {
        GetSources(
            repository = get(),
            executor = get()
        )
    }
}