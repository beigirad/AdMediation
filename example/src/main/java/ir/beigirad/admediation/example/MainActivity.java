package ir.beigirad.admediation.example;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ir.beigirad.admediation.AdMediation;
import ir.beigirad.admediation.logger.ILogger;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainScreenBinder binder = new MainScreenBinder(this) {
            @Override
            public void onInitializeClick() {
                AdMediation.initialize(
                        MainActivity.this,
                        () -> {
                            addSpecialLog("Initialization", "Done");
                            return kotlin.Unit.INSTANCE;
                        },
                        message -> {
                            addSpecialLog("Initialization", "Error: " + message);
                            return kotlin.Unit.INSTANCE;
                        });
            }

            @Override
            public void onRequestAdClick() {
                AdMediation.requestAd(
                        MainActivity.this,
                        () -> {
                            addSpecialLog("Requesting", "Done");
                            return kotlin.Unit.INSTANCE;
                        }, message -> {
                            addSpecialLog("Requesting", "Error: " + message);
                            return kotlin.Unit.INSTANCE;
                        });
            }

            @Override
            public void onShowAdClick() {
                AdMediation.showAd(
                        MainActivity.this,
                        () -> {
                            addSpecialLog("Showing", "Done");
                            return kotlin.Unit.INSTANCE;
                        },
                        message -> {
                            addSpecialLog("Showing", "Error: " + message);
                            return kotlin.Unit.INSTANCE;
                        }
                );
            }
        };
        setContentView(binder.getRootView());

        AdMediation.configure(new ILogger() {
            @Override
            public void i(@NonNull String message) {
                binder.addLog("I", message);
            }

            @Override
            public void d(@NonNull String message) {
                binder.addLog("D", message);
            }
        });
    }
}