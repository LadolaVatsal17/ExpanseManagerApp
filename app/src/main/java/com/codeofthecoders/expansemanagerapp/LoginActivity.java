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

public class LoginActivity extends AppCompatActivity
{
    private EditText mEmail;
    private EditText mPass;
    private TextView btn_login;
    private TextView sign_up;
    private TextView email_login;
    private TextView password_login;
    private TextView forgot_password;

    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email_login);
        mPass = findViewById(R.id.password_login);
        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);
        sign_up = findViewById(R.id.sign_up);


        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                login_details();
            }
        });

        //Registration activity
        sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        //Forgot password Activity
        forgot_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),ResetActivity.class));
            }
        });

    }

    private void login_details()
    {
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass))
        {
            Toast.makeText(LoginActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mDialog.setMessage("Please Wait");
            mDialog.show();

            mAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login not done ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

}