package com.codeofthecoders.expansemanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity
{
    private EditText mEmail;
    private EditText mPass;
    private TextView btn_sign;
    private TextView login_click;
    private ProgressDialog mDialog;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mEmail  = findViewById(R.id.email_registration);
        mPass = findViewById(R.id.password_registration);
        btn_sign = findViewById(R.id.btn_registration);
        login_click = findViewById(R.id.login_click);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        //Registration button function
        btn_sign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                registration_details();
            }
        });

        //Back to login screen
        login_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }


    private void registration_details()
    {
        final String email = mEmail.getText().toString().trim();
        final String password = mPass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) )
        {
            Toast.makeText(RegistrationActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6 )
        {
            Toast.makeText(RegistrationActivity.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mDialog.setMessage("Please Wait");
            mDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                mDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                mDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Not done", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}