package ir.beigirad.admediation.example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.beigirad.admediation.AdMediation
import ir.beigirad.admediation.logger.ILogger


class MainKotlinActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder = object : MainScreenBinder(this) {
            override fun onInitializeClick() {
                AdMediation.initialize(
                    context = this@MainKotlinActivity,
                    onComplete = { addSpecialLog("Initialization", "Done") },
                    onError = { addSpecialLog("Initialization", "Error: $it") }
                )
            }

            override fun onRequestAdClick() {
                AdMediation.requestAd(
                    context = this@MainKotlinActivity,
                    onComplete = { addSpecialLog("Requesting", "Done") },
                    onError = { addSpecialLog("Requesting", "Error: $it") }
                )
            }

            override fun onShowAdClick() {
                AdMediation.showAd(
                    context = this@MainKotlinActivity,
                    onComplete = { addSpecialLog("Showing", "Done") },
                    onError = { addSpecialLog("Showing", "Error: $it") }
                )
            }
        }
        setContentView(binder.rootView)

        AdMediation.configure(object : ILogger {
            override fun i(message: String) {
                binder.addLog("I", message)
            }

            override fun d(message: String) {
                binder.addLog("D", message)
            }
        })
    }
}