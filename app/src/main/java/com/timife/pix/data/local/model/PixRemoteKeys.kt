package com.timife.pix.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pix_remote_keys")
data class PixRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)