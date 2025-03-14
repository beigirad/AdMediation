package ir.beigirad.admediation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ir.beigirad.admediation.example.databinding.ActivityMainBinding;
import ir.beigirad.admediation.logger.ILogger;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdMediation.configure(new ILogger() {
            @Override
            public void i(@NonNull String message) {
                binding.tvLog.append("\nI:  " + message);
            }

            @Override
            public void d(@NonNull String message) {
                binding.tvLog.append("\nD:  " + message);
            }
        });

        binding.btnInitializer.setOnClickListener(v -> AdMediation.initialize(this));
        binding.btnRequester.setOnClickListener(v -> AdMediation.requestAd(this));
    }
}