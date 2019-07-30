package com.example.timetrackadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CompList item = allCompanyList.get(position);

        holder.myCompName.setText(item.getCompanyName());
        holder.myLoc.setText(item.getLocation());

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
