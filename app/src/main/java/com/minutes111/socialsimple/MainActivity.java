package com.minutes111.socialsimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private TwitterAuthConfig mAuthConfig;
    private TwitterLoginButton mTwitterLoginButton;
    private LoginButton mFaceLoginButton;
    private CallbackManager mCallbackManager;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "s6YhPxoZfrKoeO0SVJoW2fmka";
    private static final String TWITTER_SECRET = "gzX5mTTUKBxcrYhOkYQpdl3dmGgkymdjjrxq16D4yC0A8PrEI7";
    private static final int FACEBOOK_REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(mAuthConfig));
        FacebookSdk.sdkInitialize(getApplicationContext(), FACEBOOK_REQUEST_CODE);

        setContentView(R.layout.activity_main);
        mCallbackManager = CallbackManager.Factory.create();
        mTwitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        mFaceLoginButton = (LoginButton)findViewById(R.id.btn_login_face);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTwitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                String text = "User id:" + result.data.getUserId() + "\n" + "Auth token: "
                        + result.data.getAuthToken();
                startActivity(new Intent(MainActivity.this,MonitorActivity.class)
                                    .putExtra(Const.ATTR_INTENT,text));
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        mFaceLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String text = "User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken();
                startActivity(new Intent(MainActivity.this,MonitorActivity.class)
                        .putExtra(Const.ATTR_INTENT, text));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FACEBOOK_REQUEST_CODE){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == mAuthConfig.DEFAULT_AUTH_REQUEST_CODE){
            mTwitterLoginButton.onActivityResult(requestCode, resultCode, data);
        }
    }

}
