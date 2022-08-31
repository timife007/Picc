package com.timife.pix.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timife.pix.data.local.model.PixRemoteKeys
import com.timife.pix.domain.model.Pix


@Database(
    entities = [Pix::class, PixRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class PixDb : RoomDatabase() {
    abstract fun pixDao(): PixDao
    abstract fun pixRemoteKeysDao(): PixRemoteKeysDao
}