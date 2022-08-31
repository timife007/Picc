package com.timife.pix.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timife.pix.domain.model.Pix

@Dao
interface PixDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPixListEntity(
        pixListEntity: List<Pix>
    )

    @Query("DELETE FROM pixListEntity")
    suspend fun clearPixList()

    @Query(
        """
            SELECT *
            FROM pixListEntity
            WHERE LOWER(searchItem) LIKE '%' || LOWER(:query) || '%' OR
                  UPPER(:query) == searchItem
        """
    )
    fun searchPixListEntity(query: String): PagingSource<Int, Pix>
}