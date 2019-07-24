package com.example.timetrackadmin.view;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.User;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;

import java.util.HashMap;

public class ViewSingleUser extends AppCompatActivity {
    String id;
    TextView name, email;
    ImageView pic;
    SharedPreferenceConfig obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_user);

        obj = SharedPreferenceConfig.getInstance();

        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getString("id");

        name = (TextView) findViewById(R.id.suv_nameid);
        email = (TextView) findViewById(R.id.suv_emailid);
        pic = (ImageView) findViewById(R.id.suv_pic);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionAPI api = ServerConnection.getConnection();
        HashMap<String, String> header = new HashMap<>();
        header.put("authorization", obj.readToken());
        Call<User> call = api.getUser(header, id);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("Response: ",response.body().toString());
                    name.setText(response.body().getFirstName());
                    email.setText(response.body().getEmail());

                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
