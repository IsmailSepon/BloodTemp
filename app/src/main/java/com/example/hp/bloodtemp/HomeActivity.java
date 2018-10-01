package com.example.hp.bloodtemp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.hp.bloodtemp.R.*;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";

    private String bloodgroup,mobile,name,userID;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        bloodgroup=intent.getStringExtra("blood");
        name=intent.getStringExtra("name");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        toastMessage(userID);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


        senddata();


        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        Button fontAwesomeAndroidIcon = (Button) findViewById(id.search_go_btn);
        Button fontNotice=(Button) findViewById(id.notice_go_btn);
        Button fontPost=(Button) findViewById(id.postId);
        Button fontAddDonar=(Button) findViewById(id.addDonar);
        Button fontInfo=(Button) findViewById(id.info);
        Button fontDash=(Button) findViewById(id.dash);

        fontAwesomeAndroidIcon.setTypeface(fontAwesomeFont);
        fontNotice.setTypeface(fontAwesomeFont);
        fontPost.setTypeface(fontAwesomeFont);
        fontAddDonar.setTypeface(fontAwesomeFont);
        fontInfo.setTypeface(fontAwesomeFont);
        fontDash.setTypeface(fontAwesomeFont);

        Button btn = (Button) findViewById(id.postId);
        final Button cencelDonar=(Button) findViewById(id.cencelDonar);
        final Button cencel=(Button) findViewById(R.id.cencel);
        final LinearLayout rt = (LinearLayout)findViewById(id.postLayout);
        final CardView cd=(CardView) findViewById(R.id.home);
        final LinearLayout dn=(LinearLayout) findViewById(id.donarLayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent other = new Intent(HomeActivity.this, PostActivity.class);
                startActivity(other);
            }
        });

//        btn.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v)
//            {
//                cd.setVisibility(View.INVISIBLE);
//                rt.setVisibility(View.VISIBLE);
//                cencel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rt.setVisibility(View.INVISIBLE);
//                        cd.setVisibility(View.VISIBLE);
//                    }
//                });
//            }
//
//        });

        fontAddDonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd.setVisibility(View.INVISIBLE);
                dn.setVisibility(View.VISIBLE);

                cencelDonar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dn.setVisibility(View.INVISIBLE);
                        cd.setVisibility(View.VISIBLE);
                    }
                });
            }
        });




    }

    private void senddata() {

       //Userinfo userinfo = new Userinfo("sepon", "01912250477", "B");
       Userinfo userinfo = new Userinfo(name,mobile,bloodgroup);
       toastMessage(name+"  " +mobile+"  " +bloodgroup);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference child = myRef.child("Users");
        DatabaseReference childvalue = child.child(userID);
        childvalue.setValue(userinfo);


        //myRef.setValue("Hello, !");
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
