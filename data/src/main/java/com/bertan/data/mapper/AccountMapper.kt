package com.bertan.data.mapper

import com.bertan.data.model.AccountEntity
import com.bertan.domain.model.Account

object AccountEntityMapper {
    val AccountEntity?.asAccount: Account?
        get() = mapTo {
            Account(
                it.sourceId,
                it.id,
                it.name,
                it.icon,
                it.userName,
                it.url,
                it.createdDate,
                it.lastSyncDate
            )
        }
}

object AccountMapper {
    val Account?.asAccountEntity: AccountEntity?
        get() = mapTo {
            AccountEntity(
                it.sourceId,
                it.id,
                it.name,
                it.icon,
                it.userName,
                it.url,
                it.createdDate,
                it.lastSyncDate
            )
        }
}