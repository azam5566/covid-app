package com.carwale.covidapp.utils.extensions

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

fun isOnline(): Boolean {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val exitValue = ipProcess.waitFor()
        return exitValue == 0
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    return false
}

fun whenOnline(fn: (e: Exception?) -> Unit) {
    return try {
        val timeoutMs = 1500
        val sock = Socket()
        val sockaddr: SocketAddress = InetSocketAddress("8.8.8.8", 53)
        sock.connect(sockaddr, timeoutMs)
        sock.close()
        fn(null)
    } catch (e: IOException) {
        fn(e)
    }
}
