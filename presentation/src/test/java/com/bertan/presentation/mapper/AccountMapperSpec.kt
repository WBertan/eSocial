package com.bertan.presentation.mapper

import com.bertan.domain.model.Account
import com.bertan.presentation.mapper.AccountMapper.asAccountView
import com.bertan.presentation.mapper.AccountViewMapper.asAccount
import com.bertan.presentation.model.AccountView
import com.bertan.presentation.test.AccountDataFactory
import com.bertan.presentation.test.AccountViewDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountMapperSpec {
    @Test
    fun `given domain when mapping to View it should map`() {
        val domain = AccountDataFactory.get()
        val expectedResult =
            AccountView(
                domain.sourceId,
                domain.id,
                domain.name,
                domain.icon,
                domain.userName,
                domain.url,
                domain.createdDate,
                domain.lastSyncDate
            )

        val result = domain.asAccountView

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given View when mapping to domain it should map`() {
        val view = AccountViewDataFactory.get()
        val expectedResult =
            Account(
                view.sourceId,
                view.id,
                view.name,
                view.icon,
                view.userName,
                view.url,
                view.createdDate,
                view.lastSyncDate
            )

        val result = view.asAccount

        assertEquals(expectedResult, result)
    }
}