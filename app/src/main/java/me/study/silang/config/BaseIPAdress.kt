package me.study.silang.config

object BaseIPAdress{


    val ip = "192.168.199.225"
    val socketPort = "9000"
    val httpPort="8080"

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
