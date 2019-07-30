package com.example.timetrackadmin.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.UsersAdapter;
import com.example.timetrackadmin.model.UsersList;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        userRecyclerView = (RecyclerView) view.findViewById(R.id.users_recyclerview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userRecyclerView.setHasFixedSize(true);
        api = ServerConnection.getConnection();
        getAllUsers();
        sv = (SearchView) view.findViewById(R.id.mSearch);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                usersAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                usersAdapter.getFilter().filter(newText);
                return true;
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
        super.onCreateOptionsMenu(menu, inflater);
    }
}
