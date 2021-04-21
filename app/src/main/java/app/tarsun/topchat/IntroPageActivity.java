package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import app.tarsun.topchat.databinding.ActivityIntropageBinding;

public class IntroPageActivity extends AppCompatActivity {

    ActivityIntropageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntropageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroPageActivity.this,PhoneAuthActivity.class));
            }
        });
    }
}