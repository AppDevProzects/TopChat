package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.tarsun.topchat.databinding.ActivityOTPBinding;

public class OTP extends AppCompatActivity {

    ActivityOTPBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOTPBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        binding.VerificationTitle.setText("Verify "+phoneNumber);
    }
}