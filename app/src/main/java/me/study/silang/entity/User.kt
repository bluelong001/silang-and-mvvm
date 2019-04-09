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
class User : Serializable {

    var id: Int? = null

    var username: String? = ""

    var password: String? = ""

    var displayname: String? = ""

    var fileId: Int? = null

    var role: Int? = null
    init {
        id=null
        username=""
        password=""
        displayname=""
        fileId=null
        role=null
    }
    companion object {

        private const val serialVersionUID = 1L
    }


}
