package com.example.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    protected EditText mphoneNumber;
    protected EditText mcode;
    protected Button mverifyBtn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        userLogged();

        initView();
        mverifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartphoneNumberVerification();
            }
        });
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInwhithAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };
    }

    private void signInwhithAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userLogged();
                }
            }
        });
    }

    private void userLogged() {
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        if (User != null) {
            startActivity(new Intent(MainActivity.this
                    , HomeScreen.class));
            finish();
        }
        return;
    }

    private void StartphoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mphoneNumber.getText().toString(), 60, TimeUnit.SECONDS, this, mCallback);
    }


    private void initView() {
        mphoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mcode = (EditText) findViewById(R.id.code);
        mverifyBtn = (Button) findViewById(R.id.verify_btn);
    }
}
