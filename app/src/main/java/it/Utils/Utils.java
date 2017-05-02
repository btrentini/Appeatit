package it.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import it.Model.User;

/**
 * Created by Tobias on 01/04/2017.
 */

public class Utils {
    private static Utils ourInstance = new Utils();
    public static Utils getInstance() {
        return ourInstance;
    }

    private ProgressDialog mProgressDialog;
    private SharedPreferences sharedPreferences;
    private final String GLOBAL_DATA = "GLOBAL_DATA";


    private Utils() {
    }

    private RealmConfiguration config = new RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build();
    private Realm realmInstance = Realm.getInstance(config);


    public Realm getRealmInstance() {
        return realmInstance;
    }


    public void showProgressBar(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Aguarde");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void hideProgressBar(){
        mProgressDialog.hide();
        mProgressDialog = null;
    }


    /**
     * Login Methods
     * */
    public void setLoginUser(Context context, boolean login, User user){
        sharedPreferences = context.getSharedPreferences(GLOBAL_DATA,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(login){
            editor = sharedPreferences.edit();
            editor.putBoolean("loged",login);
            editor.putString("name",user.getName());
            editor.putString("email",user.getEmail());
            editor.putString("id",user.getStringID());
            editor.apply();
        }else {
            editor = sharedPreferences.edit();
            editor.putBoolean("loged",login);
            editor.putString("name","");
            editor.putString("email","");
            editor.putString("id","");
            editor.apply();
        }
    }

    public boolean getLoginUser(Context context){
        sharedPreferences = context.getSharedPreferences(GLOBAL_DATA,Context.MODE_PRIVATE);
        if(sharedPreferences.contains("loged")){
            if(sharedPreferences.getBoolean("loged",false)){
                return true;
            }
            return false;
        }else {
            return false;
        }
    }

    public User getLoginUserInfo(Context context){
        sharedPreferences = context.getSharedPreferences(GLOBAL_DATA,Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString("name",""));
        user.setEmail(sharedPreferences.getString("email",""));
        user.setStringID(sharedPreferences.getString("name",""));
        return user;
    }

}
