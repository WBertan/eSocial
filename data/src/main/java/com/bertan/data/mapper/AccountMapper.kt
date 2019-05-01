package com.bertan.data.mapper

import com.bertan.data.model.AccountEntity
import com.bertan.domain.model.Account

object AccountEntityMapper {
    val AccountEntity.asAccount: Account
        get() =
            Account(
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

object AccountMapper {
    val Account.asAccountEntity: AccountEntity
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