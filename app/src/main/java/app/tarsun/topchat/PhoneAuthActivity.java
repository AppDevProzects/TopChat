package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import app.tarsun.topchat.databinding.ActivityPhoneAuthBinding;

public class PhoneAuthActivity extends AppCompatActivity {

    ActivityPhoneAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextPhone.requestFocus();

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(binding.editTextPhone.getText())){
                    binding.editTextPhone.setError("No input detected");
                    return;
                }
                if(!(binding.editTextPhone.getText().toString().length()==10)){
                    binding.editTextPhone.setError("Enter valid phone number!");
                    return;
                }


                Intent intent = new Intent(PhoneAuthActivity.this, OTPActivity.class);
                intent.putExtra("code","+"+binding.ccp.getFullNumber());
                intent.putExtra("number",binding.editTextPhone.getText().toString());
                startActivity(intent);
            }
        });
    }
}