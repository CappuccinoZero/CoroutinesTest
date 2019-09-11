package com.example.coroutinestest.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coroutinestest.model.data.LoveEntity
import io.reactivex.Flowable

@Dao
interface Dao {
    @Query("SELECT * FROM loves WHERE id = :id")
    fun queryLoveData(id:String):Flowable<List<LoveEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg loveEntity: LoveEntity)
}