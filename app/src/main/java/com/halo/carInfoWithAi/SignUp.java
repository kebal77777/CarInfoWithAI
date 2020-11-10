package com.halo.carInfoWithAi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUp extends AppCompatActivity  {

    EditText mUsername;
    EditText mPassword;
    EditText mEmail;
    Button mButton;
    TextView loginRedirect;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    private static final String TAG = "SignUp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mEmail = findViewById(R.id.email);
        mButton = findViewById(R.id.userSignUp);
        loginRedirect = findViewById(R.id.tv_login);
        fAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
//        onBackPressed();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"clicked");
//                Intent intent = new Intent(SignUp.this,l.class);
                onBackPressed();
            }
        });


        //validation
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email cant be empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password cant be empty");
                    return;
                }
                if (password.length()  < 6 ) {
                    mPassword.setError("Password must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(SignUp.this, "Error"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}

