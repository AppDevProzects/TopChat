package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import app.tarsun.topchat.databinding.ActivityIntropageBinding;

public class IntroPageActivity extends AppCompatActivity {

    ActivityIntropageBinding binding;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntropageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(IntroPageActivity.this,MainActivity.class));
                }
                else{
                    startActivity(new Intent(IntroPageActivity.this,PhoneAuthActivity.class));
                }

                finish();
            }
        });
    }
}