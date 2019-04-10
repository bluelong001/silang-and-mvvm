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
class Post : Serializable {

    var id: Int? = null

    var title: String? = ""

    var content: String? = ""

    var gmtCreate: Date? = Date()

    var gmtUpdate: Date? = Date()

    var userId: Int? = null

    init {
        id=null
        title=""
        content=""
        gmtCreate=Date()
        gmtUpdate=Date()
        userId=null
    }
    companion object {

        private const val serialVersionUID = 1L
    }


}
