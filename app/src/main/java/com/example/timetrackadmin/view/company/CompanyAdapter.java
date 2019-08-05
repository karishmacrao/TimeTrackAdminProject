package com.example.timetrackadmin.view.company;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.CompList;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    Context context;
    ArrayList<CompList> allCompanyList = new ArrayList<>();

    public CompanyAdapter(ArrayList<CompList> usersList, Context context) {
        if (usersList != null && usersList.size() > 0) {
            allCompanyList.addAll(usersList);
        }
        this.context = context;
    }

    @NonNull
    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CompList item = allCompanyList.get(position);

        holder.myCompName.setText(item.getCompanyName());
        holder.myLoc.setText(item.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewSingleCompany.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", allCompanyList.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allCompanyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myCompName;
        TextView myLoc;

        public ViewHolder(View itemView) {
            super(itemView);
            myCompName = itemView.findViewById(R.id.comp_name);
            myLoc = itemView.findViewById(R.id.comp_location);
        }
    }
}
