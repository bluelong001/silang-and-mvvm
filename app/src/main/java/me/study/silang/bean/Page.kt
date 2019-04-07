package me.study.silang.bean

class Page{
    var page:Long
    var pageSize:Long
    constructor(page: Long, pageSize: Long) {
        this.page = page
        this.pageSize = pageSize
    }
    constructor() {
        this.page = 1L
        this.pageSize = 10L
    }


}
