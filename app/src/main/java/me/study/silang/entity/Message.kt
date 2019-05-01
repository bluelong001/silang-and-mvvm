package me.study.silang.entity


import java.io.Serializable

class Message : Serializable {

    var id: Int? = null

    var message: String? = null

    companion object {

        private const val serialVersionUID = 1L
    }


}
