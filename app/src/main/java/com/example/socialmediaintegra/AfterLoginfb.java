package com.example.socialmediaintegra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

public class AfterLoginfb extends AppCompatActivity {
    Button logoutButton;
    CallbackManager callbackManager;
    TextView Emailtxt,Nametxt;
    ProfilePictureView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_loginfb);

        getSupportActionBar().setTitle("Facebook Log in");

        callbackManager = CallbackManager.Factory.create();
        logoutButton = findViewById(R.id.fblogout);
        Emailtxt = findViewById(R.id.email);
        Nametxt = findViewById(R.id.name);
        profile = findViewById(R.id.ProfilePicture);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                String email;
                try {
                    String first_name = object.getString("name");
                    String id = object.getString("id");
                    try {
                        email = response.getJSONObject().getString("email");
                    }catch (Exception e){
                        email = "Email is Private"+e ;
                    }
                    Nametxt.setText("Name :  "+first_name);
                    Emailtxt.setText("Email id :  "+email);
                    profile.setProfileId(id);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ok",e.toString());
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(AfterLoginfb.this,MainActivity.class));
                finish();
            }
        });


    }
}