package com.example.timetrackadmin.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.Company;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class AddCompanyActivity extends AppCompatActivity {

    TextInputEditText code, name, location;
    MaterialButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        Toolbar toolbar = (Toolbar) findViewById(R.id.company_toolbar);
        setSupportActionBar(toolbar);


        code = (TextInputEditText) findViewById(R.id.company_code);
        name = (TextInputEditText) findViewById(R.id.company_name);
        location = (TextInputEditText) findViewById(R.id.company_location);

        submitBtn = (MaterialButton) findViewById(R.id.companySubmit);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mCode = code.getText().toString();
                String mName = name.getText().toString();
                String mLocation = location.getText().toString();
                if (mCode.isEmpty()) {
                    code.setError("Require's Company code");
                }
                if (mName.isEmpty()) {
                    name.setError("Require's Company name");
                }
                if (mLocation.isEmpty()) {
                    location.setError("Require's Company Location");
                } else {
                    ConnectionAPI api = ServerConnection.getConnection();
                    SharedPreferenceConfig spc =SharedPreferenceConfig.getInstance();
                    HashMap<String,String> header = new HashMap<>();
                    header.put("authorization", spc.readToken());

                    Company companyObj = new Company();
                    companyObj.setCompanyCode(mCode);
                    companyObj.setCompanyName(mName);
                    companyObj.setLocation(mLocation);
                    Call<Company> call = api.addCompany(header,companyObj);
                    call.enqueue(new Callback<Company>() {
                        @Override
                        public void onResponse(Call<Company> call, Response<Company> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(AddCompanyActivity.this, "Company Added Successfully", Toast.LENGTH_LONG).show();
                                name.setText("");
                                code.setText("");
                                location.setText("");
                            } else {
                                Toast.makeText(AddCompanyActivity.this, "Company Code exists already!", Toast.LENGTH_LONG).show();
                                code.setError("Company Code exists already");
                            }
                        }

                        @Override
                        public void onFailure(Call<Company> call, Throwable t) {
                            Toast.makeText(AddCompanyActivity.this, "Failed to connect", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }

}


