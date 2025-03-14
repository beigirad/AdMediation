package ir.beigirad.admediation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ir.beigirad.admediation.logger.ILogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainKotlinActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder = object : MainScreenBinder(this) {
            override fun onInitializeClick() {
                lifecycleScope.launch(Dispatchers.IO) {
                    AdMediation.initialize(this@MainKotlinActivity)
                }
            }

            override fun onRequestAdClick() {
                lifecycleScope.launch(Dispatchers.IO) {
                    AdMediation.requestAd(this@MainKotlinActivity)
                }
            }
        }
        setContentView(binder.rootView)

        AdMediation.configure(object : ILogger {
            override fun i(message: String) {
                binder.addToLog("I", message)
            }

            override fun d(message: String) {
                binder.addToLog("D", message)
            }
        })
    }
}