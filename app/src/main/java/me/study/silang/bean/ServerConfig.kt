package me.study.silang.bean

enum class ServerConfig private constructor(i: Int) {
    DEV(1), TEST(2), PROD(3);

    var ip: String? = null
        private set

    init {
        when (i) {
            1 -> ip = "127.0.0.1"
            else -> ip = "127.0.0.1"
        }

    }
}
