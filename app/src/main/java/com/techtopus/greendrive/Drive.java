package com.techtopus.greendrive;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class Drive extends AppCompatActivity {
String cartype="";
int k=0,j=0;
    CircleImageView i1,i2,i3;
    DatabaseReference dbcar;
    FirebaseAuth mAuth;
    TextView t,t1,t2,t3;
    EditText e,e2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        mAuth=FirebaseAuth.getInstance();
        i1=findViewById(R.id.circleImageView);
        t=findViewById(R.id.textView6);
        dbcar= FirebaseDatabase.getInstance().getReference("car");
        e=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        i2=findViewById(R.id.circleImageView2);
        i3=findViewById(R.id.circleimageview3);
        t1=findViewById(R.id.textView118);
        t2=findViewById(R.id.textView119);
        t3=findViewById(R.id.textView120);
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ++k;

                if(k%2==0 && k<=7)
                {

                        e.append("-");
                    --k;
                }
            }
        });
    }
    public void nextstep(View view)
    {
        String carno=e.getText().toString();
String carmodel=e2.getText().toString();
        if(!carno.matches("^[a-zA-Z]{2}[-]{1}[0-9]{2}[-]{1}[a-zA-Z]{2}[-]{1}[0-9]{4}")||carno.equals(""))
        {
            e.setError("Illegal Registration number ");
            return;
    }
    if(carmodel.equals(""))
    {
        e2.setError("Enter a valid car model");
        return;
    }
        if(cartype=="")
        {
            t.setVisibility(View.VISIBLE);
            return;
        }try {
        String id = dbcar.push().getKey();
        car c = new car(cartype, carno, mAuth.getCurrentUser().getUid(),carmodel,id);
        dbcar.child(id).setValue(c);
        Intent i=new Intent(this,tripdetails.class);
        i.putExtra("carid",id);



        Toast.makeText(this, "Car details uploaded!", Toast.LENGTH_SHORT).show();
        startActivity(i);

    }catch(Exception e)
    {
        Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
    }
    }
    public void carselect(View v)
    {
        switch (v.getId())
        {
            case R.id.circleImageView:
         cartype="bike";

         t3.setVisibility(View.VISIBLE);
         t2.setVisibility(View.GONE);
         t1.setVisibility(View.GONE);


         break;

         case R.id.circleImageView2:
             cartype="lmv";
             t2.setVisibility(View.VISIBLE);
             t3.setVisibility(View.GONE);
             t1.setVisibility(View.GONE);
             break;
            case R.id.circleimageview3:
                cartype="suv";
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                break;

        }

    }@Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }

}
