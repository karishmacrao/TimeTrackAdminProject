package com.example.timetrackadmin.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.timetrackadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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

            }
        });
    }
}
