package ir.beigirad.admediation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ir.beigirad.admediation.logger.ILogger;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainScreenBinder binder = new MainScreenBinder(this) {
            @Override
            public void onInitializeClick() {
                AdMediation.initialize(MainActivity.this);
            }

            @Override
            public void onRequestAdClick() {
                AdMediation.requestAd(MainActivity.this);
            }

            @Override
            public void onShowAdClick() {
                AdMediation.showAd(MainActivity.this);
            }
        };
        setContentView(binder.getRootView());

        AdMediation.configure(new ILogger() {
            @Override
            public void i(@NonNull String message) {
                binder.addToLog("I", message);
            }

            @Override
            public void d(@NonNull String message) {
                binder.addToLog("D", message);
            }
        });
    }
}