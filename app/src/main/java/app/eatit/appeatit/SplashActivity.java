package app.eatit.appeatit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import gcm.RegistrationIntentService;

public class SplashActivity extends AppCompatActivity {


    /*Intent Service*/
   // private BroadcastReceiver broadcastReceiver;
    private static final int PLAY_SERVICES_REQUEST = 9000;
    private boolean isReceiverRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
/*
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };*/

        registerReceiver();

        if(checkPlayServices()){
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected  void onResume(){
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause(){
       // LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        isReceiverRegister = false;
        super.onPause();
    }

    public void registerReceiver(){
        if(!isReceiverRegister){
           // LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("registrationComplete"));
            isReceiverRegister = true;
        }
    }

    public boolean checkPlayServices(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(apiAvailability.isUserResolvableError(resultCode)){
                apiAvailability.getErrorDialog(this,resultCode,PLAY_SERVICES_REQUEST).show();
            }else {
                Log.i("SplashActivity","Device not supported");
            }
            return false;
        }
        return true;
    }
}
