package it.appeatit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import it.Model.User;
import it.Utils.Utils;

public class LoginActivity extends AppCompatActivity {


    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle(R.string.attention);
                builder.setMessage(R.string.login_soon);
                builder.setPositiveButton(R.string.ok, null);
                builder.show();
            }
        });

        /**
         * Facebook login
         * */


        if(AccessToken.getCurrentAccessToken() != null){
            Log.d("DEBUG","LOGADO");
            AccessToken ac = AccessToken.getCurrentAccessToken();

        }else {
            Log.d("DEBUG","FAZ LOGIN");
            callbackManager = CallbackManager.Factory.create();
            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList(
                    "public_profile", "email"));

            // Other app specific specialization

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        Log.v("LoginActivity", response.toString());
                                        // Application code
                                        User user = new User();
                                        user.setName(object.getString("name"));
                                        user.setEmail(object.getString("email"));
                                        user.setStringID(object.getString("id"));
                                        Utils.getInstance().setLoginUser(getApplicationContext(),true,user);
                                    }catch (JSONException e){
                                        Log.d("DEBUG",e.getMessage());
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    // App code
                    Log.d("DEBUG", "Login Canceled");
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Log.d("DEBUG", exception.getMessage());
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
