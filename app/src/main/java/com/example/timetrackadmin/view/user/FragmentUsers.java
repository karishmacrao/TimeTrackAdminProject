package com.example.timetrackadmin.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.UsersList;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentUsers extends androidx.fragment.app.Fragment {
    SharedPreferenceConfig spc = SharedPreferenceConfig.getInstance();
    View view;
    SearchView sv;
    UsersAdapter usersAdapter;
    ArrayList<UsersList> usersListResponse = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    RecyclerView userRecyclerView;
    ConnectionAPI api;
    HashMap<String, String> header;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);

        Objects.requireNonNull(((AppCompatActivity) view.getContext()).getSupportActionBar()).setSubtitle("All Users");

        userRecyclerView = (RecyclerView) view.findViewById(R.id.users_recyclerview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userRecyclerView.setHasFixedSize(true);
        api = ServerConnection.getConnection();
        getAllUsers();
        FloatingActionButton btnAdduser = (FloatingActionButton) view.findViewById(R.id.btnAddUser);
        btnAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }

    public void getAllUsers() {
        header = new HashMap<>();
        header.put("authorization", spc.readToken());
        Call<ArrayList<UsersList>> call = api.getUsers(header);
        call.enqueue(new Callback<ArrayList<UsersList>>() {

            @Override
            public void onResponse(Call<ArrayList<UsersList>> call, Response<ArrayList<UsersList>> response) {

                Log.d("MM", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        usersListResponse.clear();
                        usersListResponse.addAll(response.body());
                    }
                    setDataInRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UsersList>> call, Throwable t) {

            }
        });
    }

    private void setDataInRecyclerView() {
        mLayoutManager = new LinearLayoutManager(view.getContext());
        userRecyclerView.setLayoutManager(mLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        usersAdapter = new UsersAdapter(usersListResponse, view.getContext());
        userRecyclerView.setAdapter(usersAdapter); // set the Adapter to RecyclerView
        usersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllUsers();

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu, inflater);
    }
}
