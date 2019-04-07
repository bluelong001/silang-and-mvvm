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
class File : Serializable {

    var id: Int? = null

    var type: String? = null

    var fileName: String? = null

    companion object {

        private const val serialVersionUID = 1L
    }
}
