package me.study.silang.model

import java.io.Serializable

class UserInfo : Serializable {

    var id: Int? = null

    var displayname: String? = null

    var headIcon: String? = null

    var role: Int? = null

    companion object {

        private const val serialVersionUID = 1L
    }
}
