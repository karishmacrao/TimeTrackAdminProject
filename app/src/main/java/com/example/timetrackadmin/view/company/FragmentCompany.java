package com.example.timetrackadmin.view.company;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.CompList;
import com.example.timetrackadmin.repository.ConnectionAPI;
import com.example.timetrackadmin.repository.ServerConnection;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCompany extends Fragment {
    SharedPreferenceConfig spc = SharedPreferenceConfig.getInstance();
    View view;
    CompanyAdapter companyAdapter;
    ArrayList<CompList> compListResponse = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    RecyclerView companyRecyclerView;
    ConnectionAPI api;
    HashMap<String, String> header;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar= (Toolbar)((AppCompatActivity) getActivity()).findViewById(R.id.comp_recyclerview);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("All Companies");
        setHasOptionsMenu(true);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_company, container, false);

        companyRecyclerView = (RecyclerView) view.findViewById(R.id.comp_recyclerview);
        companyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        companyRecyclerView.setHasFixedSize(true);

        api = ServerConnection.getConnection();
        getAllCompanys();

        FloatingActionButton btnAddCompany = (FloatingActionButton)view.findViewById(R.id.btnAddCompany);
        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCompany();
            }
        });
        return view;
    }

    private void getAllCompanys() {
        header = new HashMap<>();
        header.put("authorization", spc.readToken());

        Call<ArrayList<CompList>> call = api.getCompanys(header);
        call.enqueue(new Callback<ArrayList<CompList>>() {
            @Override
            public void onResponse(Call<ArrayList<CompList>> call, Response<ArrayList<CompList>> response) {
                if (response.isSuccessful()) {
                    Log.d("Company ", "Successfull");
                    if (response.body() != null) {
                        compListResponse.clear();
                        compListResponse.addAll(response.body());
                    }
                    setDataInRecyclerView();
                } else {
                    Log.d("Company ", "Failed");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<CompList>> call, Throwable t) {
                Log.d("MM", "onResponse() failed : call = [" + call + "], response = [" + t + "]");
            }
        });
    }

    private void setDataInRecyclerView() {
        mLayoutManager = new LinearLayoutManager(view.getContext());
        companyRecyclerView.setLayoutManager(mLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        companyAdapter = new CompanyAdapter(compListResponse, view.getContext());
        companyRecyclerView.setAdapter(companyAdapter); // set the Adapter to RecyclerView
        companyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllCompanys();
    }

    private void addNewCompany()
    {
        Intent intent = new Intent(view.getContext(), AddCompanyActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("id", lists.get(position).getId());
//        Log.d("MMM", lists.get(position).getId());
//        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
