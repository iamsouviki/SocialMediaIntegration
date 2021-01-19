package com.example.socialmediaintegra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class AfterLoginGoogle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_google);

        getSupportActionBar().setTitle("Google Log in");

        TextView nametx = findViewById(R.id.googlename);
        TextView emailtx = findViewById(R.id.googleemail);
        ImageView googleprofileimage = findViewById(R.id.googleprofilepic);
        Button signout = findViewById(R.id.buttongooglesignout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount != null){
            String name = googleSignInAccount.getDisplayName();
            String email = googleSignInAccount.getEmail();
            Uri photo_url = googleSignInAccount.getPhotoUrl();
            Log.e("Profile",name+"  "+email+"  "+photo_url);
            nametx.setText("  Name : "+name+"  ");
            emailtx.setText("  Email : "+email+"  ");
            //googleprofileimage.setImageURI(photo_url);
            Picasso.get().load(photo_url).into(googleprofileimage);
        }
    }

    private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(AfterLoginGoogle.this, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(AfterLoginGoogle.this,MainActivity.class));
                finish();
            }
        });
    }
}