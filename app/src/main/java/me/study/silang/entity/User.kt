package me.study.silang.entity

import java.io.Serializable

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

    var username: String? = null

    var password: String? = null

    var displayname: String? = null

    var headIcon: String? = null

    var role: Int? = null

    companion object {

        private const val serialVersionUID = 1L
    }


}
