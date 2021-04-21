package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.tarsun.topchat.databinding.ActivityOTPBinding;

public class OTPActivity extends AppCompatActivity {

    ActivityOTPBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOTPBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String phoneNumber = getIntent().getStringExtra("code")+getIntent().getStringExtra("number");
        //System.out.println(getIntent().getStringExtra("code"));
        binding.VerificationTitle.setText("Verify "+getIntent().getStringExtra("code")+"-"+getIntent().getStringExtra("number"));

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPActivity.this,Register_Activity.class));
                finish();
            }
        });
    }
}