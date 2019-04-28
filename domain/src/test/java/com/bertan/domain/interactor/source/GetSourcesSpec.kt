package com.bertan.domain.interactor.source

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.repository.Repository
import com.bertan.domain.test.SourceDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetSourcesSpec {
    private lateinit var getSources: GetSources

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getSources = GetSources(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getSources() } returns Observable.just(emptyList())

        val result = getSources.buildUseCase().test()

        result.assertComplete()
    }

    @Test
    fun `given a response when executes it should return data ordered by name`() {
        val sourceA = SourceDataFactory.get().copy(name = "sourceA")
        val sourceB = SourceDataFactory.get().copy(name = "sourceB")
        val sources = listOf(sourceB, sourceA)
        every { repository.getSources() } returns Observable.just(sources)

        val result = getSources.buildUseCase().test()

        result.assertValue(listOf(sourceA, sourceB))
    }
}