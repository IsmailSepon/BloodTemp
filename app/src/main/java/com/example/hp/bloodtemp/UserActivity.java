package com.example.hp.bloodtemp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private static final String REGISTER_URL = "http://wwwtestahcom.000webhostapp.com/registation/userregistation.php";

    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_BLOODGROUP="blood_group";

    EditText userName, userContact, userBloodGroup;
    Button userReg;
    SQLiteDatabase db;
    FirebaseAuth auth;
    ProgressBar progressBar;

    List<String> Category;
    AppCompatSpinner categorySpinner;
    ArrayAdapter<String> categoryAdapter;
    String categoryParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        categorySpinner = (AppCompatSpinner)findViewById(R.id.category);

        setCategorySpinner();

        userName=(EditText) findViewById(R.id.userName);
        userContact=(EditText) findViewById(R.id.userContact);
      //  userBloodGroup=(EditText) findViewById(R.id.userBloodGroup);
        userReg=(Button) findViewById(R.id.userReg);





        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String username = userName.getText().toString().trim();
                final String usercontact = userContact.getText().toString().trim();
               // final String userbloodgroup = userBloodGroup.getText().toString().trim();




                if(usercontact.isEmpty() || usercontact.length() < 10){
                    userContact.setError("Enter a valid mobile");
                    userContact.requestFocus();
                    return;
                }

                Intent intent = new Intent(UserActivity.this, verifyActivity.class);
                intent.putExtra("mobile", usercontact);
                intent.putExtra("group", categoryParam);
                intent.putExtra("name", username);
                startActivity(intent);




                }


        });

    }

//    public void showMessage(String title,String message)
//    {
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//
//        //this feom oncreat code
//
////                db=openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
////
////                if(userName.getText().toString().trim().length()==0||
////                            userContact.getText().toString().trim().length()==0||
////                            userBloodGroup.getText().toString().trim().length()==0)
////                    {
////                        showMessage("Error", "Please enter all values");
////                        return;
////                    }
////                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
////                        new Response.Listener<String>() {
////                            @Override
////                            public void onResponse(String response) {
////                                Toast.makeText(UserActivity.this,response,Toast.LENGTH_LONG).show();
////                            }
////                        },
////                        new Response.ErrorListener() {
////                            @Override
////                            public void onErrorResponse(VolleyError error) {
////                                Toast.makeText(UserActivity.this,error.toString(),Toast.LENGTH_LONG).show();
////                            }
////                        }){
////                    @Override
////                    protected Map<String,String> getParams(){
////                        Map<String,String> params = new HashMap<String, String>();
////                        params.put(KEY_USERNAME,username);
////                        params.put(KEY_CONTACT,usercontact);
////                        params.put(KEY_BLOODGROUP, userbloodgroup);
////
////                        return params;
////                    }
////
////                };
////
////                RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
////                requestQueue.add(stringRequest);
////
////                    db.execSQL("INSERT INTO user_info VALUES('"+userName.getText()+"','"+userContact.getText()+
////                            "','"+userBloodGroup.getText()+"');");
////                    showMessage("Success", "Record added");
////
////                Intent home= new Intent(UserActivity.this, HomeActivity.class);
////                startActivity(home);
////                finish();
//    }
//    public void clearText()
//    {
//        userName.setText("");
//        userContact.setText("");
//        userBloodGroup.setText("");
//
//    }

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
