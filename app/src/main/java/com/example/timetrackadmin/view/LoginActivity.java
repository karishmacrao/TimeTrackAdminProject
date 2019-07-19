package com.example.timetrackadmin.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.User;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText username, password;
    MaterialButton login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        username = (TextInputEditText) findViewById(R.id.usernameId);
        password = (TextInputEditText) findViewById(R.id.passwordId);
        login = (MaterialButton) findViewById(R.id.loginButtonId);
        register = (MaterialButton) findViewById(R.id.regSubmit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("authorization", "Bearer ");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String usernameStr = Objects.requireNonNull(username.getText()).toString();
                final String passwordStr = Objects.requireNonNull(password.getText()).toString();

                if (usernameStr.isEmpty() || !usernameStr.matches(emailPattern)) {
                    username.setError("Enter valid email address");

                } else if (passwordStr.isEmpty() || passwordStr.length() < 6) {
                    password.setError("Enter a valid password");

                } else {

                    loginFunction(usernameStr, passwordStr);

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });
    }

    private void toastText(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public LiveData<Boolean> loginFunction(final String usernameStr, final String passwordStr) {

        final MutableLiveData<Boolean> result = new MutableLiveData<Boolean>();
        ServerConnection conn = new ServerConnection();
        ConnectionAPI api = conn.getConnection();
        JSONObject object = new JSONObject();

        try {
            object.put("username", usernameStr);
            object.put("password", passwordStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; utf-8"), object.toString());
        Call<User> call = api.loginUser(requestBody);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User localUser = response.body().getUsers();
                    User userOb = response.body();
                    //                                Log.d("LoginActivity", "onResponse() called with: call = [" + call + "], response = [" + response + "]" + userOb.getToken());
                    SharedPreferenceConfig SPCObject = SharedPreferenceConfig.getInstance();
                    SPCObject.writeLoginStatus(true);
                    SPCObject.writeUserEmail(usernameStr);
                    SPCObject.writeUserPassword(passwordStr);
                    SPCObject.writeFirstName(localUser.getFirstName());
                    SPCObject.writeLastName(localUser.getLastName());
                    SPCObject.writeUserEmail(localUser.getEmail());
                    SPCObject.writeToken("Bearer " + userOb.getToken());
                    result.postValue(true);
                    toastText("Logged in successfully");

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    toastText("Incorrect username or password");
                    result.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("LoginActivity", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                toastText("Failed to login");
                result.postValue(false);

            }
        });
        return result;
    }
}
