package ir.beigirad.admediation.example

import android.app.Activity
import android.os.Bundle
import ir.beigirad.admediation.AdMediation

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdMediation.initialize()
    }
}