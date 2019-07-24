package com.example.timetrackadmin.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentUsers extends androidx.fragment.app.Fragment {
    SharedPreferenceConfig spc = SharedPreferenceConfig.getInstance();
    View view;
    ArrayList<UsersList> usersListResponse = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    RecyclerView userRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);

        userRecyclerView = (RecyclerView) view.findViewById(R.id.users_recyclerview);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userRecyclerView.setHasFixedSize(true);

        final ArrayList<UsersList> itemArrayList = new ArrayList<>();
        ConnectionAPI api = ServerConnection.getConnection();
        HashMap<String, String> header = new HashMap<>();
        header.put("authorization", spc.readToken());
        Call<ArrayList<UsersList>> call = api.getUsers(header);
        call.enqueue(new Callback<ArrayList<UsersList>>() {

            @Override
            public void onResponse(Call<ArrayList<UsersList>> call, Response<ArrayList<UsersList>> response) {

                Log.d("MM", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        usersListResponse.addAll(response.body());
                    }
                    setDataInRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UsersList>> call, Throwable t) {

            }
        });
        return view;
    }

    private void setDataInRecyclerView() {
        mLayoutManager = new LinearLayoutManager(view.getContext());
        userRecyclerView.setLayoutManager(mLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        UsersAdapter usersAdapter = new UsersAdapter(usersListResponse, view.getContext());
        userRecyclerView.setAdapter(usersAdapter); // set the Adapter to RecyclerView
    }


}
