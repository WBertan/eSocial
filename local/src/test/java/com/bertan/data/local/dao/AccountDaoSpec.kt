package com.bertan.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import com.bertan.data.local.db.LocalDatabase
import com.bertan.data.local.test.AccountModelDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AccountDaoSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database =
        Room
            .inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.applicationContext,
                LocalDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()

    @After
    fun tearDown() = database.close()

    @Test
    fun `given a model when insert it should save it`() {
        val model = AccountModelDataFactory.get()
        database.accountDao.insert(model)

        val result = database.accountDao.accounts().test()
        result.assertValue(listOf(model))
    }

    @Test
    fun `given an existing model when insert it should replace it`() {
        val model = AccountModelDataFactory.get()
        database.accountDao.insert(model)
        database.accountDao.insert(model.copy(name = "otherName"))

        val result = database.accountDao.accounts().test()
        result.assertValue(listOf(model.copy(name = "otherName")))
    }

    @Test
    fun `given a response when call accounts it should return all accounts`() {
        val models = AccountModelDataFactory.get(2)
        database.accountDao.insert(models.first())
        database.accountDao.insert(models.last())

        val result = database.accountDao.accounts().test()
        result.assertValue(models)
    }

    @Test
    fun `given a found response when call account it should return the account`() {
        val models = AccountModelDataFactory.get(2)
        database.accountDao.insert(models.first())
        database.accountDao.insert(models.last())

        val result = database.accountDao.account(models.first().id).test()
        result.assertValue(models.first())
    }

    @Test
    fun `given a not found response when call account it should return an EmptyResultSetException`() {
        val models = AccountModelDataFactory.get(2)
        database.accountDao.insert(models.first())
        database.accountDao.insert(models.last())

        val result = database.accountDao.account("notFoundId").test()
        result.assertError(EmptyResultSetException::class.java)
    }
}