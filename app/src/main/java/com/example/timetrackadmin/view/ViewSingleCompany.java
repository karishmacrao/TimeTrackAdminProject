package com.example.timetrackadmin.view;

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
import android.widget.Toast;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.Company;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;

import java.util.HashMap;

public class ViewSingleCompany extends AppCompatActivity {
    String id;
    ConnectionAPI api;
    HashMap<String, String> header;
    SharedPreferenceConfig obj = SharedPreferenceConfig.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_company_view);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getString("id");
        Log.d("ViewSingleCompany: ",id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.company_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Company");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.singleview_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.delete_id) {
            customDialog(
                    "Delete Company permanently",
                    "Do you want to delete Company record permanently?",
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
                deletecompanypermanently();
                toast("Deleted successfully!");
                finish();
            }
        });
        builderSingle.show();
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void deletecompanypermanently() {
        header = new HashMap<>();
        header.put("authorization", obj.readToken());
        api= ServerConnection.getConnection();
        Call<Company> call = api.deleteCompany(header, id);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {

            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {

            }
        });

    }
}
