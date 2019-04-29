package com.techtopus.greendrive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class personalinfo extends AppCompatActivity {
TextView t115;
ImageView i43;
    private static final int CHOOSE_IMAAGE =101 ;
    private static final int PLACE_PICKER_REQUEST = 101;
    private RadioButton radioSexButton;
    private ProgressBar progressBar;
    private ImageView img;
    private Uri uriprofileimg;
    private FirebaseAuth mAuth;
    public String gender;
    int p;
    String profileImgUrl;
    String token;
    private RadioGroup radioSexGroup;
    private EditText name,aadhar,phone,age,sex;
    DatabaseReference dbuser;
    private DatabaseReference RootRef;
    EditText home;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        FirebaseApp.initializeApp(this);
        Toolbar toolbar=findViewById(R.id.toolbar);
        home=findViewById(R.id.editText11);
        p=0;
        b=findViewById(R.id.button);
        i43=findViewById(R.id.imageView43);
        t115=findViewById(R.id.textView115);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        dbuser= FirebaseDatabase.getInstance().getReference("user");
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        name = (EditText) findViewById(R.id.fullname);
        img=(ImageView)findViewById(R.id.imageView7);
        aadhar = (EditText) findViewById(R.id.aadhar);
        phone = (EditText) findViewById(R.id.phno);
        //FirebaseMessaging.getInstance().subscribeToTopic("udpates");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                   if(task.isSuccessful()){
                    token=task.getResult().getToken();
                    //   Toast.makeText(personalinfo.this, "token : "+token , Toast.LENGTH_SHORT).show();
                   }
                   else
                       Toast.makeText(personalinfo.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            uriprofileimg=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriprofileimg);
                img.setImageBitmap(bitmap);
                uploadImagesToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }/*
        if(requestCode == PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                home.setVisibility(View.VISIBLE);
                t115.setVisibility(View.GONE);

                i43.setVisibility(View.GONE);
                home.setText(place.getName());
}
        }
*/
    }

    private void uploadImagesToFirebase() {


        final StorageReference profileImageRef= FirebaseStorage.getInstance().getReference("profilepics/"+mAuth.getCurrentUser().getUid()+".jpg");
        if(uriprofileimg!=null)

        {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            //progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriprofileimg)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressBar.setVisibility(View.GONE);
                            profileImgUrl= profileImageRef.getDownloadUrl().toString();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // progressBar.setVisibility(View.GONE);
                    Toast.makeText(personalinfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.menu ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login.class));
                break;

        }
        return true;
    }

    public void selectpic(View v)
    {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i.createChooser(i,"Select your profile picture"),CHOOSE_IMAAGE);


    }



    public void upload(View v)
    {

        String namevar=name.getText().toString();
        String aadharvar=aadhar.getText().toString();
        String phonevar=phone.getText().toString();
        if(namevar.equals(""))
        {
            name.setError("Enter Name!");
            name.requestFocus();
            return;
        }

        if( aadharvar.length()!=16)
        {
            aadhar.setError("Enter valid Aadhar number!");
            aadhar.requestFocus();
            return;
        }
        if( phonevar.length()!=10)
        {
            phone.setError("Enter valid mobile number!");
            phone.requestFocus();
            return;
        }
        if(p==0)
        {
            Toast.makeText(this, "Please pin your home location", Toast.LENGTH_SHORT).show();
            return;

        }
        b.setEnabled(false);
        String custid=mAuth.getCurrentUser().getUid();
        DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("user");
        String id = databasereference2.push().getKey();

        String s2="https://firebasestorage.googleapis.com/v0/b/greendrive-c916f.appspot.com/o/profilepics%2F"+
                mAuth.getCurrentUser().getUid()+".jpg?alt=media&token=dcccb28a-25dc-4822-9952-9ec0740ac5b0";
        user u=new user(namevar,mAuth.getCurrentUser().getUid(),
                mAuth.getCurrentUser().getEmail(),gender,phonevar,aadharvar,token,s2);
        databasereference2.child(id).setValue(u);




        FirebaseUser user=mAuth.getCurrentUser();
if(profileImgUrl==null)
{
    Toast.makeText(this, "Select a profile picture", Toast.LENGTH_SHORT).show();
    return;
}
        if(user!=null&&profileImgUrl!=null)
        {
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(namevar)
                    .setPhotoUri(Uri.parse(profileImgUrl)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(personalinfo.this, "Your Profile updated successfully!!", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(personalinfo.this,welcome.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(personalinfo.this, "Oops!Some error occured!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else
            startActivity(new Intent(this, Login.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        { finish();
            startActivity(new Intent(this, Login.class));
        }}

    public void selectfrmmap(View v)
    {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
p=1;
        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }
    View view;


    public void onRadioButtonClicked(View v)
    {
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioSexButton = (RadioButton) findViewById(selectedId);
        gender=radioSexButton.getText().toString();

       // Toast.makeText(this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();
    }
}
