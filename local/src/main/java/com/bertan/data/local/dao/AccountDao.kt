package com.bertan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bertan.data.local.db.Constants.Accounts
import com.bertan.data.local.model.AccountModel
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: AccountModel)

    @Query("SELECT * FROM ${Accounts.TABLE_NAME}")
    fun accounts(): Observable<List<AccountModel>>

    @Query("SELECT * FROM ${Accounts.TABLE_NAME} WHERE id = :accountId")
    fun account(accountId: String): Single<AccountModel>
}