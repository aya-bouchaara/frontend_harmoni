package com.example.harmoni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.harmoni.helpers.StringHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class register extends AppCompatActivity {

    EditText UserName, email, password, confirm;
    Button sign_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Hook Edit Text Fields:
        UserName  = findViewById(R.id.etRUserName);
        email       = findViewById(R.id.etREmail);
        password    = findViewById(R.id.etRPassword);
        confirm     = findViewById(R.id.etRConfirm);

        // Hook Sign Up Button:
        sign_up_btn = findViewById(R.id.btnRegister);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFormFields();
            }
        });
    }
    // End Of On Create Method.

    public void goToHome(View view){
        Intent intent = new Intent(register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSigInAct(View view){
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
        finish();
    }

    public void processFormFields(){
        // Check For Errors:
        if(!validateUserName() || !validateEmail() || !validatePasswordAndConfirm()){
            return;
        }
        // End Of Check For Errors.

        // Instantiate The Request Queue:
        RequestQueue queue = Volley.newRequestQueue(register.this);
        // The URL Posting TO:
        String url = "http://10.1.6.48:9000/signup";

        // Set JSON Body:
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", UserName.getText().toString());
            jsonBody.put("email", email.getText().toString());
            jsonBody.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // String Request Object:
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equalsIgnoreCase("User created successfully")){
                    UserName.setText(null);
                    email.setText(null);
                    password.setText(null);
                    confirm.setText(null);
                    Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                }
                // End Of Response If Block.

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(register.this, "Erreur", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonBody.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };// End Of String Request Object.

        queue.add(stringRequest);
    }
    // End Of Process Form Fields Method.


    public boolean validateUserName(){
        String UserName = this.UserName.getText().toString();
        // Check If FUserName Is Empty:
        if(UserName.isEmpty()){
            this.UserName.setError("UserName cannot be empty!");
            return false;
        }else{
            this.UserName.setError(null);
            return true;
        }// Check If UserName Is Empty.
    }
    // End Of Validate UserName Field.
    public boolean validateEmail(){
        String email_e = email.getText().toString();
        // Check If Email Is Empty:
        if(email_e.isEmpty()){
            email.setError("Email cannot be empty!");
            return false;
        }else if(!StringHelper.regexEmailValidationPattern(email_e)){
            email.setError("Please enter a valid email");
            return false;
        }else{
            email.setError(null);
            return true;
        }// Check If Email Is Empty.
    }
    // End Of Validate Email Field.

    public boolean validatePasswordAndConfirm(){
        String password_p = password.getText().toString();
        String confirm_p = confirm.getText().toString();

        // Check If Password and Confirm Field Is Empty:
        if(password_p.isEmpty()){
            password.setError("Password cannot be empty!");
            return false;
        }else if (!password_p.equals(confirm_p)){
            password.setError("Passwords do not match!");
            return false;
        }else if(confirm_p.isEmpty()){
            confirm.setError("Confirm field cannot be empty!");
            return false;
        }else{
            password.setError(null);
            confirm.setError(null);
            return true;
        }// Check Password and Confirm Field Is Empty.
    }
    // End Of Validate Password and Confirm Field.



}
