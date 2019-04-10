package me.study.silang.bean

class Page {
    var page: Long
    var pageSize: Long

    constructor(page: Long, pageSize: Long) {
        this.page = page
        this.pageSize = pageSize
    }

    constructor() {
        this.page = 1L
        this.pageSize = 9L
    }

    fun next() {
        page += 1
        pageSize = 9L
    }

    fun back() {
        page -= 1
        pageSize = 9L
    }
}
