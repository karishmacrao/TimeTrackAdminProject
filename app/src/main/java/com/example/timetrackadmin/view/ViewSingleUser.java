package com.example.timetrackadmin.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    ConnectionAPI api;
    HashMap<String, String> header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.suv_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        api = ServerConnection.getConnection();
        header = new HashMap<>();
        header.put("authorization", obj.readToken());
        Call<User> call = api.getUser(header, id);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("Response: ", response.body().toString());
                    name.setText(response.body().getFirstName());
                    email.setText(response.body().getEmail());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Failue", t.toString());

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.user_delete_id) {
            customDialog(
                    "Delete user permanently",
                    "Do you want to delete user record permanently?",
                    "Cancel",
                    "Ok");
        }
        return super.onOptionsItemSelected(item);

    }

    public void customDialog(String title, String message, final String cancelmethod, final String okmethod) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);

        builderSingle.setIcon(R.drawable.ic_delete_forever_black_24dp);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builderSingle.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteuserpermanently();
                toast("Deleted successfully!");
                finish();
            }
        });
        builderSingle.show();
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void deleteuserpermanently(){
        Call<User> call = api.deleteUser(header, id);
        call.enqueue(new Callback<User>(){

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
