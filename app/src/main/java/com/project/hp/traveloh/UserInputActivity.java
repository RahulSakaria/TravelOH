package com.project.hp.traveloh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class UserInputActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailID, password, retypePassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button createAccount;
    private DatabaseReference databaseReference;
    private EditText fullname, dateofBirth, number;
    private int day_x, month_x, year_x;
    static final int DIALOG = 0;
    private ImageView circleImageView;
    private static int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorage;
    public Uri uri, downloadUrl;
    private RadioGroup gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.project.hp.traveloh.R.layout.activity_user_input);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        circleImageView = (ImageView) findViewById(com.project.hp.traveloh.R.id.profile_image_user_inputs);
        emailID = (EditText) findViewById(com.project.hp.traveloh.R.id.enter_person_emailid);
        password = (EditText) findViewById(com.project.hp.traveloh.R.id.enter_person_password);
        retypePassword = (EditText) findViewById(com.project.hp.traveloh.R.id.retypepassword);
        createAccount = (Button) findViewById(com.project.hp.traveloh.R.id.createAccount);
        createAccount.setOnClickListener(this);
        fullname = (EditText) findViewById(com.project.hp.traveloh.R.id.full_name_enter);
        dateofBirth = (EditText) findViewById(com.project.hp.traveloh.R.id.date_of_birth);
        gender = (RadioGroup) findViewById(com.project.hp.traveloh.R.id.gender1);
        number = (EditText) findViewById(com.project.hp.traveloh.R.id.phone_number_register);
        dateofBirth.setOnClickListener(this);
        final Calendar calendar = Calendar.getInstance();
        circleImageView.setOnClickListener(this);
        day_x = calendar.get(Calendar.DATE);
        month_x = calendar.get(Calendar.MONTH);
        year_x = calendar.get(Calendar.YEAR);
        ShowDialogOnButtonClick();


    }

    @Override
    public void onClick(View view) {
        if (view == createAccount) {
            registerUser();
        }
        if (view == circleImageView) {
            showFileChooser();
        }
    }

    private void registerUser() {

        String email = emailID.getText().toString().trim();
        String passwd = password.getText().toString().trim();
        String retypepwd = retypePassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(retypepwd)) {
            Toast.makeText(this, "Please Retype Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwd.equals(retypepwd)) {
            progressDialog.setMessage("Registering...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SaveUserInfo();
                                uploadFile();

                            } else {
                                Toast.makeText(UserInputActivity.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
        } else {
            progressDialog.dismiss();
            Toast.makeText(UserInputActivity.this, "Retype Correct Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveUserInfo() {
        String mname = fullname.getText().toString().trim();
        String genders = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
        String emails = emailID.getText().toString().trim();
        String dates = dateofBirth.getText().toString().trim();
        String numbers = number.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserInformationData userInformationData = new UserInformationData(mname, genders, emails, dates, numbers);
        databaseReference.child(user.getUid()).setValue(userInformationData);
        Toast.makeText(UserInputActivity.this, "User Data Saved", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Toast.makeText(UserInputActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(UserInputActivity.this, BaseNavigationDrawerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void ShowDialogOnButtonClick() {

        dateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG)
            return new DatePickerDialog(this, datepickerListener, year_x, month_x, day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener datepickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;

            dateofBirth.setText(day_x + "/" + month_x + "/" + year_x);
        }
    };

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), CAMERA_REQUEST_CODE);
    }

    private void uploadFile() {
        if (uri != null) {
            final StorageReference upload = mStorage.child("Profile Photo").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userphoto");
            upload.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUrl = taskSnapshot.getDownloadUrl();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseReference.child(user.getUid()).child("profile photo").setValue(downloadUrl.toString());

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}