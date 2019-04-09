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
class PostModel : Serializable {


    var id: Int? = 0

    var title = ""

    var content = ""

    var gmtCreate = "2018-01-01 00:00:00"

    var gmtUpdate = "2018-01-01 00:00:00"

    var userInfo: UserInfo? = null

    var replyNum: Int? = 0

    companion object {

        private const val serialVersionUID = 1L
    }
}
