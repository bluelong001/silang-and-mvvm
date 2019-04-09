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
class File : Serializable {

    var id: Int? = null

    var type: String? = ""

    var fileName: String? = ""
    init {
        id=null
        type=""
        fileName=""
    }
    companion object {

        private const val serialVersionUID = 1L
    }
}
