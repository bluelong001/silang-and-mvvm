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
class Reply : Serializable {

    var id: Int? = null

    var context: String? = ""

    var gmtCreate: Date? =  Date()

    var gmtUpdate: Date? =  Date()

    var userId: Int? = null

    var postId: Int? = null
    init {
        id=null
        context=""
        postId=null
        gmtCreate=Date()
        gmtUpdate=Date()
        userId=null
    }
    companion object {

        private const val serialVersionUID = 1L
    }


}
