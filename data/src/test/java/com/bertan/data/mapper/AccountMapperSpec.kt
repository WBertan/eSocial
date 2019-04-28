package com.bertan.data.mapper

import com.bertan.data.mapper.AccountEntityMapper.asAccount
import com.bertan.data.mapper.AccountMapper.asAccountEntity
import com.bertan.data.model.AccountEntity
import com.bertan.data.test.AccountDataFactory
import com.bertan.data.test.AccountEntityDataFactory
import com.bertan.domain.model.Account
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountMapperSpec {
    @Test
    fun `given domain when mapping to entity it should map`() {
        val domain = AccountDataFactory.get()
        val expectedResult =
            AccountEntity(
                domain.sourceId,
                domain.id,
                domain.name,
                domain.icon,
                domain.userName,
                domain.url,
                domain.createdDate,
                domain.lastSyncDate
            )

        val result = domain.asAccountEntity

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given entity when mapping to domain it should map`() {
        val entity = AccountEntityDataFactory.get()
        val expectedResult =
            Account(
                entity.sourceId,
                entity.id,
                entity.name,
                entity.icon,
                entity.userName,
                entity.url,
                entity.createdDate,
                entity.lastSyncDate
            )

        val result = entity.asAccount

        assertEquals(expectedResult, result)
    }
}