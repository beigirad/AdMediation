package ir.beigirad.admediation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ir.beigirad.admediation.example.databinding.ActivityMainBinding
import ir.beigirad.admediation.logger.ILogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainKotlinActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(binding.getRoot())

        AdMediation.configure(object : ILogger {
            override fun i(message: String) {
                binding.tvLog.append("\nI:  " + message)
            }

            override fun d(message: String) {
                binding.tvLog.append("\nD:  " + message)
            }
        })

        binding.btnInitializer.setOnClickListener(View.OnClickListener { v: View? ->
            lifecycleScope.launch(Dispatchers.IO) {
                AdMediation.initialize(this@MainKotlinActivity)
            }
        })
    }
}