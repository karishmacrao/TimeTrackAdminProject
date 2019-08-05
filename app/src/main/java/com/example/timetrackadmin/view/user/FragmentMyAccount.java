package com.example.timetrackadmin.view.user;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.User;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMyAccount extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    TextInputEditText FName, LName, Email, Pass;
    MaterialButton myUpdateBtn;
    String username, id;
    View view;
    UsersAdapter usersAdapter;
    SharedPreferenceConfig obj = SharedPreferenceConfig.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        Objects.requireNonNull(((AppCompatActivity) view.getContext()).getSupportActionBar()).setSubtitle("My Account");
        FName = (TextInputEditText) view.findViewById(R.id.myFNameId);
        LName = (TextInputEditText) view.findViewById(R.id.myLNameId);
        Email = (TextInputEditText) view.findViewById(R.id.myEmail);
        Pass = (TextInputEditText) view.findViewById(R.id.myPass);
        myUpdateBtn = (MaterialButton) view.findViewById(R.id.myUpdate);

        FName.setText(obj.readFirstName());
        LName.setText(obj.readLastName());
        Email.setText(obj.readUserEmail());
        username = obj.readUserEmail();
        id = obj.readId();
        myUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatemyaccount();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void updatemyaccount() {
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        String firstname = FName.getText().toString();
        String lastname = LName.getText().toString();
        String companyemail = Email.getText().toString();
        String password = Pass.getText().toString();

        if (firstname.isEmpty()) {
            FName.setError("Require's First Name");
        }
        if (lastname.isEmpty()) {
            LName.setError("Require's Last Name");
        }
        if (companyemail.isEmpty() || !companyemail.matches(emailPattern)) {
            Email.setError("Require's valid Email");
        }
        if (password.isEmpty() || password.length() < 6) {
            Pass.setError("Require's a valid Password");
        } else {
            ConnectionAPI api = ServerConnection.getConnection();
            final User userObj = new User();

            userObj.setFirstName(firstname);
            userObj.setLastName(lastname);
            userObj.setEmail(companyemail);
            userObj.setPassword(password);
            userObj.setToken(obj.readToken());
            HashMap<String, String> header = new HashMap<>();
            header.put("authorization", obj.readToken());
            Call<User> call = api.updateUser(header, id, userObj);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d("Response : ", response.toString());
                    if (response.isSuccessful()) {
                        SharedPreferenceConfig spc = SharedPreferenceConfig.getInstance();
                        spc.writeUserEmail(userObj.getEmail());
                        spc.writeFirstName(userObj.getFirstName());
                        spc.writeLastName(userObj.getLastName());
                        spc.writeUserPassword(userObj.getPassword());
                        Toast.makeText(view.getContext(), "Saved successfully.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("ERROR: ", t.getMessage());
                    Toast.makeText(view.getContext(), "Failed to update.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        SearchManager searchManager = (SearchManager) view.getContext().getSystemService(Context.SEARCH_SERVICE);
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
}
