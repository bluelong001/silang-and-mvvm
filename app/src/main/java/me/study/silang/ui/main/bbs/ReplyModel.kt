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
class ReplyModel : Serializable {

    var id: Int? = null

    var context: String? = null

    var gmtCreate: String? = null

    var gmtUpdate: String? = null

    var userName: String? = null

    companion object {

        private const val serialVersionUID = 1L
    }


}
