package it.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialize.util.UIUtils;
import com.squareup.picasso.Picasso;

import it.Model.User;
import it.Utils.Utils;
import it.appeatit.LoginActivity;
import it.appeatit.R;

/**
 * Created by Tobias on 07/04/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public void setupToolbar(Toolbar toolbar){
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
    }

    public void setupToolbar(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
    }

    public void setupNavigationDrawer(final Activity activity){

        new DrawerBuilder().withActivity(activity).build();
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Login with facebook");


        //create the drawer and remember the `Drawer` result object

        String name;
        int drawable = 0;
/*
        if(Utils.getInstance().getLoginUser(getApplicationContext())){
            User user = Utils.getInstance().getLoginUserInfo(getApplicationContext());
            name = user.getName();
            String url = "https://graph.facebook.com/"+user.getStringID()+"/picture?type=large";
            Picasso.with(getApplicationContext()).load(url);
        }else {
        */
            name = "Appeatit";
            drawable = R.mipmap.ic_launcher;
        //}


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.color.primary)
                .withTextColor(Color.BLACK)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(name)
                                .withIcon(R.mipmap.ic_launcher)
                )
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(position == 3){
                            Intent intent = new Intent();
                            intent.setClass(activity, LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);



    }

}
