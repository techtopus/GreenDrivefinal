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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Timer;
import java.util.TimerTask;

public class register extends AppCompatActivity {

    private ProgressBar progressBar;

    private Button reg;
    private FirebaseAuth mAuth;

    private EditText emailid,pass,conpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
reg=findViewById(R.id.button4);
        emailid = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        conpass = (EditText) findViewById(R.id.pass2);

        if(getIntent().getStringExtra("email")!=null)
            emailid.setText(getIntent().getStringExtra("email"));

    }
    boolean isEmail(String  text)
    {
        CharSequence email=text.toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }



    public void signup(View v)
    {

        final String email = emailid.getText().toString().trim();
        String password = pass.getText().toString().trim();//
        String password2 = conpass.getText().toString().trim();


        if(isEmail(email)==false)
        {
            emailid.setError("Enter valid Email!");
            emailid.requestFocus();
            return;
        }
        if(password.length()<4)
        {
            pass.setError("Enter a valid password for Min 4 charecters!");
            pass.requestFocus();
            return;
        }
        if(!password2.equals(password)||password2.isEmpty())
        {
            conpass.setError("The Passwords doesn't match ..please type carefully");
            conpass.requestFocus();
            return;

        }
reg.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(register.this, "Succeffully registered", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(register.this,personalinfo.class);
                            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(register.this, "Oops! You are already registered user!!\nYou will be redirected to sign in page\n in 5 seconds!!", Toast.LENGTH_SHORT).show();
                                new Timer().schedule(new TimerTask(){
                                    public void run() {
                                        Intent i=new Intent(register.this, Login.class);
                                        i.putExtra("email",email);
                                        startActivity(i);
                                    }
                                }, 5000);
                            }

                            else
                                Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, personalinfo.class));

        }
    }

    public void login(View v)
    {finish();
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}
