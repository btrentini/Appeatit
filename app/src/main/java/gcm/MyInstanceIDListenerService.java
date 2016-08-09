package gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Tobias on 29/07/2016.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {


    @Override
    public void onTokenRefresh(){
        Intent intent = new Intent(this,RegistrationIntentService.class);
        startService(intent);
    }
}
