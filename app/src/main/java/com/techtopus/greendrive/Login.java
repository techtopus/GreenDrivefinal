package com.techtopus.greendrive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {
    EditText email,pass;
    Button b;
    FirebaseAuth mauth;
    String emailid,passwor;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mauth=FirebaseAuth.getInstance();
        email= findViewById(R.id.loginemail);
        b=findViewById(R.id.button6);
        pass= findViewById(R.id.loginpassword);
        pb= findViewById(R.id.progress);

        if(getIntent().getStringExtra("email")!=null)
        {
            String em = getIntent().getStringExtra("email");
            email.setText(em);
        }
    }
    boolean isEmail(String  text)
    {
        CharSequence email=text.toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    public void frgtpass(View v)
    {
        emailid=email.getText().toString().trim();
        if (TextUtils.isEmpty(emailid))
        {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        mauth.sendPasswordResetEmail(emailid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pb.setVisibility(View.GONE);
                if (task.isSuccessful())
                    Toast.makeText(Login.this, "Reset code is sent to your mail! :)", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Login.this, "Some  error occured!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void login(View v)
    {
        emailid=email.getText().toString().trim();
        passwor=pass.getText().toString().trim();
        if (isEmail(emailid) == false)
        {
            email.setError("Enter valid Email!");
            email.requestFocus();
            return;
        }
        if (passwor.length() < 4)
        {
            pass.setError("Enter a valid password for Min 6 charecters!");
            pass.requestFocus();
            return;
        }
        b.setEnabled(false);
        pb.setVisibility(View.VISIBLE );
        mauth.signInWithEmailAndPassword(emailid,passwor).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb.setVisibility(View.GONE );
                if(task.isSuccessful())
                {
                    finish();
                    Intent i=new Intent(Login.this,welcome.class);
                    i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthInvalidUserException)
                    {                    Toast.makeText(Login.this, "Oops! You are not a registered user...\n you will be redirected to sign up page in 5 seconds!!", Toast.LENGTH_SHORT).show();
                        new Timer().schedule(new TimerTask(){
                            public void run() {
                                Intent i=new Intent(Login.this,register.class);
                                i.putExtra("email",emailid);
                                startActivity(i);
                            }
                        }, 5000);
                    }
                    else if(task.getException() instanceof FirebaseAuthWeakPasswordException){
                        {
                            Toast.makeText(Login.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                            pass.setError("Incoreect Password!");
                            pass.setText("");
                            pass.requestFocus();
                        }}
                    else
                    {
                        Toast.makeText(Login.this, "Some Error occured! Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }



    public void registerpage(View v)
    {
        finish();

        Intent i= new Intent(this,register.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mauth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, DriveORRide.class));

        }
    }
}
