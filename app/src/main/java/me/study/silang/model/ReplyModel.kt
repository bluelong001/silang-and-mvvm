package me.study.silang.model

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
class ReplyModel : Serializable {

    var id: Int? = 0

    var context = ""

    var gmtCreate = "2018-01-01 00:00:00"

    var gmtUpdate = "2018-01-01 00:00:00"

    var userInfo: UserInfo? = null

    companion object {

        private const val serialVersionUID = 1L
    }


}
