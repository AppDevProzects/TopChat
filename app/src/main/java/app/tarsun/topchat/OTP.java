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

        String phoneNumber = getIntent().getStringExtra("code")+getIntent().getStringExtra("number");
        //System.out.println(getIntent().getStringExtra("code"));
        binding.VerificationTitle.setText("Verify "+getIntent().getStringExtra("code")+"-"+getIntent().getStringExtra("number"));
    }
}