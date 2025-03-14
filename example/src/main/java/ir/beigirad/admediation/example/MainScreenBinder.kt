package ir.beigirad.admediation.example

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.method.ScrollingMovementMethod
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import ir.beigirad.admediation.example.databinding.ActivityMainBinding

abstract class MainScreenBinder(context: Context) {
    private val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
        .apply {
            (context as Activity).setTitle(context.getString(R.string.app_name) + " [${BuildConfig.FLAVOR}]")

            tvLog.movementMethod = ScrollingMovementMethod()
            btnInitializer.setOnClickListener { onInitializeClick() }
            btnRequester.setOnClickListener { onRequestAdClick() }
            btnShower.setOnClickListener { onShowAdClick() }
            btnLogCleaner.setOnClickListener { tvLog.text = "" }
        }
    val rootView: View get() = binding.root

    fun addSpecialLog(flag: String, message: String) {
        appendToLog(flag, message, color = Color.RED)
    }

    fun addLog(flag: String, message: String) {
        appendToLog(flag, message)
    }

    private fun appendToLog(flag: String, message: String, color: Int = Color.TRANSPARENT) {
        fun TextView.scrollToEnd() {
            scrollTo(0, (layout.getLineTop(lineCount) - height).coerceAtLeast(0))
        }

        binding.tvLog.text = buildSpannedString {
            append(binding.tvLog.text)

            inSpans(BackgroundColorSpan(Color.LTGRAY)) {
                append(" ")
                append(flag)
                append("  ")
            }

            if (color != Color.TRANSPARENT)
                inSpans(BackgroundColorSpan(color)) {
                    append("  ")
                    appendLine(message)
                }
            else {
                append("  ")
                appendLine(message)
            }
        }

        binding.tvLog.scrollToEnd()
    }

    abstract fun onInitializeClick()
    abstract fun onRequestAdClick()
    abstract fun onShowAdClick()
}