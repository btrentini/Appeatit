package it.Base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import it.appeatit.R;

/**
 * Created by Tobias on 07/04/2017.
 */

public class BaseActivity extends AppCompatActivity {



    public void setupToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
    }

    public void setupToolbar(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

}
