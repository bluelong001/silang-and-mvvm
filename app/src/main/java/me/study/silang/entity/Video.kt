package me.study.silang.entity

import java.io.Serializable
import java.util.*


/**
 *
 *
 *
 *
 *
 * @author Me
 * @since 2019-03-24
 */
class Video : Serializable {

    var id: Int? = null

    var videoName: String? = ""

    var content: String? = ""

    var title: String? = ""

    var gmtCreate: Date? = Date()

    var gmtUpdate: Date? =  Date()

    var userId: Int? = null

    var fileId: Int? = null
    init {
        id=null
        title=""
        content=""
        videoName=""
        fileId=null
        gmtCreate=Date()
        gmtUpdate=Date()
        userId=null
    }
    companion object {

        private const val serialVersionUID = 1L
    }


}
