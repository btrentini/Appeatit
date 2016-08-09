package gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import app.eatit.appeatit.R;


/**
 * Created by Tobias on 29/07/2016.
 */
public class RegistrationIntentService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationIntentService(String name) {
        super(name);
    }

    public RegistrationIntentService(){
        super("IntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            /* Subscribe token to topic*/
            SharedPreferences sharedPreferences = getSharedPreferences("deviceToken", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("deviceToken",token);
            editor.commit();
            Log.i("Intent","deviceToken - "+token);
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token,"/topics/appeatit-notification",null);

            //TODO Armazenar o token local e enviar pro servidor ao fazer login


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
