package com.example.harmoni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class login extends AppCompatActivity {

    TextView Forgotpass; //Forgotpassword
    EditText et_UserName, et_password;
    Button sign_in_btn;

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Forgotpass =findViewById(R.id.textView5);
        Forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(login.this , forgotpass.class);
                startActivity(intent);
            }
        });
        // Hook Edit Text Fields:
        et_UserName = findViewById(R.id.etUserName);
        et_password = findViewById(R.id.etPassword);

        // Hook Button:
        sign_in_btn = findViewById(R.id.btnLogin);

        // Set Sign In Button On Click Listener:
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });


    }
    // End Of On Create Activity.

    public void authenticateUser(){
        // Check For Errors:
        if( !validateUserName() || !validatePassword()){
            return;
        }
        // End Of Check For Errors.

        // Instantiate The Request Queue:
        RequestQueue queue = Volley.newRequestQueue(login.this);
        // The URL Posting TO:
        String url = "http://10.1.6.48:9000/authenticate";

        // Set JSON Body:
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", et_UserName.getText().toString());
            jsonBody.put("password", et_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set Request Object:
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO: Traitement de la réponse reçue du serveur ici
                        token = response;
                        // Set Intent Actions:
                        Intent goToProfile = new Intent(login.this, dashboard2.class);
                        // blast location ra 5s tkon dachboard
                        // Pass Values To Profile Activity:
                        // Start Activity:
                        startActivity(goToProfile);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(login.this, "Login Failed", Toast.LENGTH_LONG).show();
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
        };// End Of Set Request Object.
        queue.add(stringRequest);
    }


    public void goToHome(View view){
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    // End Of Go To Home Intent Method.


    public void goToSigUpAct(View view){
        Intent intent = new Intent(login.this, register.class);
        startActivity(intent);
        finish();
    }
    // End Of Go To Sign Up Intent Method.


    public boolean validateUserName(){
        String UserName = et_UserName.getText().toString();
        // Check If UserName Is Empty:
        if(UserName.isEmpty()){
            et_UserName.setError("UserName cannot be empty!");
            return false;
        }else{
            et_UserName.setError(null);
            return true;
        }// Check If UserName Is Empty.
    }
    // End Of Validate UserName Field.

    public boolean validatePassword() {
        String password = et_password.getText().toString();

        // Check If Password and Confirm Field Is Empty:
        if (password.isEmpty()) {
            et_password.setError("Password cannot be empty!");
            return false;
        } else {
            et_password.setError(null);
            return true;
        }// Check Password and Confirm Field Is Empty.
    }
    // End Of Validate Password;
}
// End Of Sign In Activity Class.




//Dans ce code, nous avons créé un objet JSON contenant le nom d'utilisateur et le mot de passe entrés
// par l'utilisateur. Ensuite, nous avons créé une requête POST en utilisant Volley et nous avons inclus
// le corps JSON dans la requête. Enfin, nous avons traité la réponse sous forme de chaîne de caractères
// dans la méthode onResponse.