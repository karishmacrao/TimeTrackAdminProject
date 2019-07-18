package com.example.timetrackadmin.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceConfig {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferenceConfig sharedPreferenceConfig;
    private static SharedPreferences.Editor editor;

    private SharedPreferenceConfig(Context context) {
        if (sharedPreferences == null) {
            //creating shared preferences space(call it sharedDatabase)
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            //creating the editor to sharedDatabase
            editor = sharedPreferences.edit();
        }
    }

    public static void init(Context context) {
        if (sharedPreferenceConfig == null) {
            sharedPreferenceConfig = new SharedPreferenceConfig(context);
        }
    }

    public static SharedPreferenceConfig getInstance() {
        return sharedPreferenceConfig;
    }

    public void writeLoginStatus(boolean loginStatus) {
        editor.putBoolean("login_status", loginStatus).commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean("login_status", false);
    }

    public void writeUserEmail(String uname) {
        editor.putString("user_email", uname).commit();
    }

    public String readUserEmail() {
        return sharedPreferences.getString("user_email", "example@company.comm");
    }

    public void writeUserPassword(String pass) {
        editor.putString("user_password", pass).commit();
    }

    public String readUserPassword() {
        return sharedPreferences.getString("user_password", "");
    }

    public void writeFirstName(String name) {
        editor.putString("first_name", name);
        editor.commit();
    }

    public String readFirstName() {
        return sharedPreferences.getString("first_name", "");
    }

    public String readToken() {
        return sharedPreferences.getString("token","");
    }

    public void writeToken(String token) {
        editor.putString("token",token).commit();
    }
}
