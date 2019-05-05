package com.bertan.data.local.mapper

import com.bertan.data.local.mapper.AccountEntityMapper.asAccountModel
import com.bertan.data.local.mapper.AccountModelMapper.asAccountEntity
import com.bertan.data.local.model.AccountModel
import com.bertan.data.local.test.AccountEntityDataFactory
import com.bertan.data.local.test.AccountModelDataFactory
import com.bertan.data.model.AccountEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountMapperSpec {
    @Test
    fun `given entity when mapping to model it should map`() {
        val entity = AccountEntityDataFactory.get()
        val expectedResult =
            AccountModel(
                entity.sourceId,
                entity.id,
                entity.name,
                entity.icon,
                entity.userName,
                entity.url,
                entity.createdDate,
                entity.lastSyncDate
            )

        val result = entity.asAccountModel

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given model when mapping to entity it should map`() {
        val model = AccountModelDataFactory.get()
        val expectedResult =
            AccountEntity(
                model.sourceId,
                model.id,
                model.name,
                model.icon,
                model.userName,
                model.url,
                model.createdDate,
                model.lastSyncDate
            )

        val result = model.asAccountEntity

        assertEquals(expectedResult, result)
    }
}