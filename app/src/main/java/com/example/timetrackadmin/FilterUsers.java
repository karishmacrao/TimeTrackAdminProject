package com.example.timetrackadmin;

import android.widget.Filter;

import com.example.timetrackadmin.model.UsersList;

import java.util.ArrayList;


public class FilterUsers extends Filter {
    UsersAdapter adapter;
    ArrayList<UsersList> filterList;
    ArrayList<UsersList> filteredusers;

    public FilterUsers(ArrayList<UsersList> filterList, UsersAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();

            filteredusers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getFirstName().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredusers.add(filterList.get(i));
                }
            }
        } else {
            results.count = filteredusers.size();
            results.values = filteredusers;
        }
        return results;

    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.searchedUsers = (ArrayList<UsersList>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
