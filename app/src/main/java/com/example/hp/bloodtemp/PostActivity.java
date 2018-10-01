package com.example.hp.bloodtemp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PostActivity extends AppCompatActivity {

   // private String url= "http://api.mimsms.com/api/sendsms/plain?";
    private String url= "http://api.mimsms.com/api/sendsms/plain?user=ismailhossainsepon&password=Blood12345&sender=Friend&SMSText=EmergencyBloodNeeded&GSM=8801612250477";

    EditText address, group, contactnumber;
    String postaddress,postnumber;
    List<String> Category;
    AppCompatSpinner categorySpinner;
    ArrayAdapter<String> categoryAdapter;
    String categoryParam;
    Button postbtn;

    private static final String TAG = "ViewDatabase";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    String userID;
    String[] numberlist = {};
    String number="8801912250477";


   public String massage = "Blood Need in this "+postaddress+" And the Group is "+categoryParam+" Contact Number: "+postnumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        categorySpinner = (AppCompatSpinner)findViewById(R.id.category2);
        setCategorySpinner();

        address= findViewById(R.id.address);
        postaddress = address.getText().toString().trim();


        contactnumber = findViewById(R.id.contactnumber);
        postnumber = contactnumber.getText().toString().trim();

        postbtn= findViewById(R.id.postbtn);
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
                sendSms();
            }
        });

    }

    private void post() {
        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("Users");
        //DatabaseReference zone1Ref = zonesRef.child("uNumber");

        zonesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                     number = zoneSnapshot.child("uNumber").getValue(String.class);
                    //Log.i(TAG, zoneSnapshot.child("uNumber").getValue(String.class));


//                    ArrayList<String> array  = new ArrayList<>();
//                    array.add(number);
                   Toast.makeText(getApplicationContext(),number,Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

    }

    private void sendSms() {
        final ProgressDialog progressbar = new ProgressDialog(PostActivity.this);
        progressbar.setMessage("Loading...");
        progressbar.setCanceledOnTouchOutside(false);
        progressbar.show();

        RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressbar.dismiss();
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.dismiss();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });

     //   {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("user", "ismailhossainsepon");
//                headers.put("password", "Blood12345");
//                headers.put("sender", "Friend");
//                headers.put("SMSText", "Massage");
//                headers.put("GSM", "8801612250477");
//                return headers;
//            }
    //    };

//
        requestQueue.add(stringRequest);
    }

    public void setCategorySpinner()
    {

        Category = new ArrayList<String>();
        Category.add("B+");
        Category.add("B-");
        Category.add("A+");
        Category.add("A-");
        Category.add("O+");
        Category.add("O-");
        Category.add("AB+");
        Category.add("AB-");
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Category);
        categoryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                categoryParam=Category.get(i).toString();
                Toast.makeText(getApplicationContext(), categoryParam, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
// private String url= "http://api.mimsms.com/api/sendsms/plain?user=ismailhossainsepon&password=Blood12345&sender=Friend&SMSText=Massage&GSM=8801612250477";