package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.tarsun.topchat.databinding.ActivityPhoneAuthBinding;

public class PhoneAuthActivity extends AppCompatActivity {

    ActivityPhoneAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneAuthActivity.this,OTP.class);
                intent.putExtra("phoneNumber",binding.editTextPhone.getText().toString());
                startActivity(intent);
            }
        });
    }
}