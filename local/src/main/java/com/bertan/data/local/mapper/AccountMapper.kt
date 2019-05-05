package com.bertan.data.local.mapper

import com.bertan.data.local.model.AccountModel
import com.bertan.data.model.AccountEntity

object AccountModelMapper {
    val AccountModel.asAccountEntity: AccountEntity
        get() =
            AccountEntity(
                sourceId,
                id,
                name,
                icon,
                userName,
                url,
                createdDate,
                lastSyncDate
            )
}

object AccountEntityMapper {
    val AccountEntity.asAccountModel: AccountModel
        get() =
            AccountModel(
                sourceId,
                id,
                name,
                icon,
                userName,
                url,
                createdDate,
                lastSyncDate
            )
}