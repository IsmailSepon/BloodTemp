package com.example.hp.bloodtemp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         //int SPLASH_TIME_OUT=4000;
       /* {

        }
*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS user_info(name VARCHAR,contact VARCHAR,bloodgroup VARCHAR);");
                Cursor c=db.rawQuery("SELECT * FROM user_info", null);

                if(c.getCount()==0) {
                    Toast.makeText(MainActivity.this, "Please Add your info first.", Toast.LENGTH_LONG).show();
                    Intent home1 = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(home1);
                }else {
                    Intent home = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(home);
                }
            }
        }, 4000);







    }

}
