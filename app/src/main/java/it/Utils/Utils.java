package it.Utils;

import android.app.ProgressDialog;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Tobias on 01/04/2017.
 */

public class Utils {
    private static Utils ourInstance = new Utils();
    public static Utils getInstance() {
        return ourInstance;
    }
    private ProgressDialog mProgressDialog;



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
}
