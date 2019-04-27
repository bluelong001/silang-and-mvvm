package me.study.silang.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class VideoCache{

    @PrimaryKey
    var id:Int?=null

    @ColumnInfo
    var localUrl:String? =null

    @ColumnInfo
    var content:String?=null

    @ColumnInfo
    var title:String?=null

    @ColumnInfo
    var cacheDate:String?=null

    @ColumnInfo
    var userId:Int?=null
}
