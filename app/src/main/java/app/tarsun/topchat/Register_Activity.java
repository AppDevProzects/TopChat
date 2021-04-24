package app.tarsun.topchat;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText firstname,lastname,email;
    TextView dateOfBirth,genderHeading;
    Button submit;
    FloatingActionButton editImage;
    CircleImageView userImage;
    String fiestnameString,lastnameString,emailString,dateOfBirthString,genderString="--Select--",userID,phoneNumber;
    Spinner gender;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        firstname = findViewById(R.id.userFirstname);
        lastname = findViewById(R.id.userLastName);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        email = findViewById(R.id.userEmail);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        submit = findViewById(R.id.submit);
        gender = findViewById(R.id.gender);
        genderHeading = findViewById(R.id.genderHeading);
        editImage = findViewById(R.id.editImage);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
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
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item_layout,genderchoose);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiestnameString = firstname.getText().toString().trim();
                lastnameString = lastname.getText().toString().trim();
                emailString = email.getText().toString().trim();
                System.out.println(genderString);
                if (fiestnameString.equals("")) {
                    firstname.setError(" Name Required ");
                    return;
                }
                if (genderString.equals("--Select--")){
                    genderHeading.setError("Please Select The Gender");
                    return;
                }

                dateOfBirthString = dateOfBirth.getText().toString();
                if (dateOfBirthString.equals("Calender")){
                    dateOfBirth.setError("Required");
                    return;
                }
                DocumentReference documentReference = fStore.collection("users").document(userID);
                HashMap<String,Object> user = new HashMap<>();
                user.put("firstName",fiestnameString);
                user.put("lastName",lastnameString);
                System.out.println(dateOfBirthString);
                user.put("DateOfBirth",dateOfBirthString);
                user.put("email",emailString);
                user.put("gender",genderString);
                user.put("phone",phoneNumber);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        
                    }
                });
                Intent intent = new Intent(Register_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        genderString = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please Select The Gender", Toast.LENGTH_SHORT).show();
    }
}