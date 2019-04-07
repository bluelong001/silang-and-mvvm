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

    var context: String? = null

    var gmtCreate: Date? = null

    var gmtUpdate: Date? = null

    var userId: Int? = null

    var postId: Int? = null

    companion object {

        private const val serialVersionUID = 1L
    }


}
