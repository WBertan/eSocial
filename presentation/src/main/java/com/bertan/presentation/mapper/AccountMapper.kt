package com.bertan.presentation.mapper

import com.bertan.domain.model.Account
import com.bertan.presentation.model.AccountView

object AccountViewMapper {
    val AccountView.asAccount: Account
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
    val Account.asAccountView: AccountView
        get() =
            AccountView(
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