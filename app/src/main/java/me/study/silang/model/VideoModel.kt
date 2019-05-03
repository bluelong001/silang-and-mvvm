package me.study.silang.model

import lombok.Builder
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
class VideoModel : Serializable {

    var id: Int? = 0

    var content = ""

    var title = ""
    var imgList = ""
    var gmtCreate = "2018-01-01 00:00:00"

    var gmtUpdate = "2018-01-01 00:00:00"

    var userInfo: UserInfo? = null

    var fileUrl: String? = null
    var videoHead: String? = null

    companion object {

        private const val serialVersionUID = 1L
    }
}
