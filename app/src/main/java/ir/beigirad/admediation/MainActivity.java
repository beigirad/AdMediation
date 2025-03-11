package ir.beigirad.admediation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.beigirad.admediation.example.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInitializer.setOnClickListener(v -> AdMediation.initialize());
    }
}