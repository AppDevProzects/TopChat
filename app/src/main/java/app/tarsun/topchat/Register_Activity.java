package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText firstname,lastname,email;
    TextView dateOfBirth;
    Button submit;
    FloatingActionButton editImage;
    CircleImageView userImage;
    String fiestnameString,lastnameString,emailString,dayOfBirthString,monthOfBirthString,yearOfBirthString;
    Spinner gender;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        firstname = findViewById(R.id.userFirstname);
        lastname = findViewById(R.id.userLastName);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        email = findViewById(R.id.userEmail);
        submit = findViewById(R.id.submit);
        gender = findViewById(R.id.gender);
        editImage = findViewById(R.id.editImage);
        userImage = findViewById(R.id.userImage);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        dateOfBirth.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        String[] genderchoose = getResources().getStringArray(R.array.gender);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,genderchoose);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiestnameString = firstname.getText().toString();
                lastnameString = lastname.getText().toString();
                emailString = email.getText().toString();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.gender);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}