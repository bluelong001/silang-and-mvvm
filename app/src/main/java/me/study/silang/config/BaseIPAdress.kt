package me.study.silang.config

object BaseIPAdress{


    val ip = "wjb666.top"
    val socketPort = "9000"
    val httpPort="8085"

    @JvmStatic
    fun getHttpAddress():String
    {
       return "http://" + ip + ":" + httpPort + "/api/"
    }

    @JvmStatic
    fun getSocketIOAddress():String
    {
        return "http://" + ip + ":" + socketPort +"/"
    }
}
