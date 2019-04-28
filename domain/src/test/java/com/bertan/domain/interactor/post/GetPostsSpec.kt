package com.bertan.domain.interactor.post

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.repository.Repository
import com.bertan.domain.test.PostDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetPostsSpec {
    private lateinit var getPosts: GetPosts

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getPosts = GetPosts(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getPosts() } returns Observable.just(emptyList())

        val result = getPosts.buildUseCase().test()

        result.assertComplete()
    }

    @Test
    fun `given a response when executes it should return data`() {
        val Posts = PostDataFactory.get(2)
        every { repository.getPosts() } returns Observable.just(Posts)

        val result = getPosts.buildUseCase().test()

        result.assertValue(Posts)
    }
}