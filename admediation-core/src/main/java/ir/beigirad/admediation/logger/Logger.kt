package ir.beigirad.admediation.logger

import android.util.Log

interface ILogger {
    fun i(message: String)
    fun d(message: String)
}

internal class DefaultLogger : ILogger {
    private val TAG = "AdMediationSdk"

    override fun i(message: String) {
        Log.i(TAG, message)
    }

    override fun d(message: String) {
        Log.v(TAG, message)
    }
}

object Logger {
    @Volatile
    private var loggers = listOf<ILogger>(DefaultLogger())
    fun addPrinter(iLogger: ILogger) {
        synchronized(loggers) {
            loggers = loggers.plus(iLogger)
        }
    }

    fun i(message: String) {
        loggers.forEach { it.i(message) }
    }

    fun d(message: String) {
        loggers.forEach { it.d(message) }
    }
}

