package com.timife.pix.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timife.pix.data.local.model.PixRemoteKeys

@Dao
interface PixRemoteKeysDao {

    @Query("SELECT * FROM pix_remote_keys WHERE id = :pixId")
    suspend fun getPixRemoteKeys(pixId: Int): PixRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPixRemoteKeys(pixRemoteKeys: List<PixRemoteKeys>)

    @Query("DELETE FROM pix_remote_keys")
    suspend fun deleteAllPixRemoteKeys()
}