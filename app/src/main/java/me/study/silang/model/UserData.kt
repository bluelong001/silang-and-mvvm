package me.study.silang.model

import java.io.Serializable


class UserData : Serializable {

    var videoNum: String? = "0"

    var postNum: String? = "0"

    var readNum: String? = "0"

    var replyNum: String? = "0"


    init {
         videoNum = "0"

         postNum = "0"

         readNum = "0"

         replyNum = "0"
    }
    companion object {

        private const val serialVersionUID = 1L
    }
}