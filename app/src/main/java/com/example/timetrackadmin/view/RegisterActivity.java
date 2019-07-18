package com.example.timetrackadmin.view;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.User;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText fname, lname, comEmail, userpassword;
    MaterialButton submitBtn;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Register a new user!");

        fname = (TextInputEditText) findViewById(R.id.regFNameId);
        lname = (TextInputEditText) findViewById(R.id.regLNameId);
        comEmail = (TextInputEditText) findViewById(R.id.regEmail);
        userpassword = (TextInputEditText) findViewById(R.id.regPass);
        submitBtn = (MaterialButton) findViewById(R.id.regSubmit);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
                String FName = Objects.requireNonNull(fname.getText()).toString();
                String LName = Objects.requireNonNull(lname.getText()).toString();
                String email = Objects.requireNonNull(comEmail.getText()).toString();
                String pwd = Objects.requireNonNull(userpassword.getText()).toString();
                if (FName.isEmpty()) {
                    fname.setError("Require's First Name");
                }
                if (LName.isEmpty()) {
                    lname.setError("Require's Last Name");
                }
                if (email.isEmpty() || !email.matches(emailPattern)) {
                    comEmail.setError("Require's valid Email");
                }
                if (pwd.isEmpty() || pwd.length() < 6) {
                    userpassword.setError("Require's a valid Password");
                } else {
                    ConnectionAPI api = ServerConnection.getConnection();
//                        JSONObject object = new JSONObject();
//                        try {
//                            object.put("username", email);
//                            object.put("firstName",FName );
//                            object.put("lastName", LName);
//                            object.put("password", pwd);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

//                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; utf-8"), object.toString());
                    User userObj = new User();
                    userObj.setFirstName(FName);
                    userObj.setLastName(LName);
                    userObj.setEmail(email);
                    userObj.setPassword(pwd);

                    Call<User> call = api.registerUser(userObj);

                    call.enqueue(new Callback<User>() {

                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                fname.setText("");
                                lname.setText("");
                                comEmail.setText("");
                                userpassword.setText("");

                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });
    }
}
