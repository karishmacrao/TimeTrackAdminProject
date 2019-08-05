package com.example.timetrackadmin.view.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.model.UsersList;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> implements Filterable {

    Context context;
    FilterUsers filter;
    ArrayList<UsersList> lists = new ArrayList<>();
    ArrayList<UsersList> searchedUsers = new ArrayList<>();

    public UsersAdapter(ArrayList<UsersList> usersList, Context context) {

        searchedUsers = usersList;

        if (usersList != null && usersList.size() > 0) {
            lists.addAll(searchedUsers);
        }
        this.context = context;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final UsersList item = lists.get(position);

        holder.myEmail.setText(item.getEmail());
        holder.myFirstName.setText(item.getFirstName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewSingleUser.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", lists.get(position).getId());
                Log.d("MMM", lists.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myFirstName;
        TextView myEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            myFirstName = itemView.findViewById(R.id.user_nameId);
            myEmail = itemView.findViewById(R.id.user_emailId);
        }
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterUsers(searchedUsers, this);
        }

        return filter;
    }
}
