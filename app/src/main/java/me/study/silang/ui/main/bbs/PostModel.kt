package me.study.silang.ui.main.bbs

import lombok.*
import lombok.experimental.Accessors

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

    var id: Int? = null

    var title: String? = null

    var content: String? = null

    var gmtCreate: String? = null

    var gmtUpdate: String? = null

    var userName: String? = null

    var replyNum: Int? = null

    companion object {

        private const val serialVersionUID = 1L
    }
}
