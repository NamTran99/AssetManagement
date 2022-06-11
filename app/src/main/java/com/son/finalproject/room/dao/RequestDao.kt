package com.son.finalproject.room.dao;

import androidx.room.Dao
import androidx.room.Query
import com.son.finalproject.base.BaseDao
import com.son.finalproject.data.Request
import com.son.finalproject.utils.RoomExtension

@Dao
interface RequestDAO: BaseDao<Request> {

    @Query("select * from ${RoomExtension.TABLE_REQUEST}")
    suspend fun getAllRequest(): List<Request>

    @Query("select * from ${RoomExtension.TABLE_REQUEST} where staffCode = :userID")
    suspend fun getRequestByUserID(userID: String): List<Request>
}