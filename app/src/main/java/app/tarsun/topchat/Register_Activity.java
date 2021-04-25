package app.tarsun.topchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private FirebaseStorage storage;

    DatePickerDialog.OnDateSetListener setListener;


    private int PERMISSION_CODE = 175605;
    private int GALLERY_ACCESS_REQUEST_CODE = 1509;
    private Uri imageUri;
    int flag = 0;
    private static String imageUrl;

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
        storage =  FirebaseStorage.getInstance();

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


        StorageReference reference = storage.getReference().child("ProfileImages").child(fAuth.getUid());
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions,PERMISSION_CODE);
                    }else{
                        getImageFromGallery();
                    }
                }else{
                    getImageFromGallery();
                }

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
                user.put("DateOfBirth",dateOfBirthString);
                user.put("email",emailString);
                user.put("gender",genderString);
                user.put("phone",phoneNumber);
                if(flag==1){
                    reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();
                                    System.out.println(imageUrl);
                                    user.put("imageUrl",imageUrl);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                                }
                            });
                        }
                    });
                }else if(flag==0){
                    user.put("imageUrl","0");
                }
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

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_ACCESS_REQUEST_CODE);        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getImageFromGallery();
            }else{
                Toast.makeText(this, "Permission Denied!!\n Felt sad for you that you are unable to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK&&requestCode == GALLERY_ACCESS_REQUEST_CODE&& data!=null){
            if(data.getData()!=null){
                userImage.setImageURI(data.getData());
                imageUri = data.getData();
                flag = 1;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        genderString = parent.getSelectedItem().toString();
        if(flag==0){
            if(genderString.equals("Female"))
            userImage.setImageResource(R.drawable.ic_woman);
            if(genderString.equals("Male"))
                userImage.setImageResource(R.drawable.ic_man);
            if(genderString.equals(("--Select--")))
                userImage.setImageResource(R.drawable.facecircle);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please Select The Gender", Toast.LENGTH_SHORT).show();
    }
}