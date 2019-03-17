package me.study.silang.bean


import lombok.Data

import java.io.Serializable

class Rest<T> : Serializable {

    var data: T? = null
        set(data) {
            field = this.data
        } //服务端数据
    var status: Int? = SUCCESS
        set(status) {
            field = this.status
        } //状态码
    var msg = ""
        set(msg) {
            field = this.msg
        } //描述信息
    var total: Int? = 0
        set(total) {
            field = this.total
        }

    fun msg(e: Throwable): Rest<*> {
        this.msg = e.toString()
        return this
    }

    fun msg(msg: String): Rest<*> {
        this.msg = msg
        return this
    }

    fun data(data: T): Rest<*> {
        this.data = data
        return this
    }

    fun status(status: Int?): Rest<*> {
        this.status = status
        return this
    }

    companion object {

        private const val serialVersionUID = -4577255781088498763L

        private val SUCCESS = 200
        private val FAIL = 201
        private val UNAUTHORIZED = 203
    }
}