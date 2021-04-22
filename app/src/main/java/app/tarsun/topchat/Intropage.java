package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Intropage extends AppCompatActivity {

    CircleImageView iconimage;
    TextView appslogen;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intropage);
        iconimage = findViewById(R.id.iconimage);
        appslogen = findViewById(R.id.appslogen);
        nextButton = findViewById(R.id.nextButton);

        delay(1250);
        iconimage.setVisibility(View.VISIBLE);
        delay(3250);
        appslogen.setVisibility(View.VISIBLE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register_Activity.class));
                finish();

            }
        });
    }

    public void delay(int milisec){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(milisec);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}