package com.inspectionsheetdemo.Login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import eu.thirdspaceauto.akka.hacksprint.Drawer.ModelActivity;
import eu.thirdspaceauto.akka.hacksprint.R;
import eu.thirdspaceauto.akka.hacksprint.Utils.LogUtils;
import eu.thirdspaceauto.akka.hacksprint.Utils.PreferencesManager;
import eu.thirdspaceauto.akka.hacksprint.Utils.StringConstants;
import eu.thirdspaceauto.akka.hacksprint.Utils.Utility;
import eu.thirdspaceauto.akka.hacksprint.network.APIClient;
import eu.thirdspaceauto.akka.hacksprint.network.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//import io.realm.Realm;

public class LoginJourneyActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "LoginActivity";
    ProgressBar progressBar;
    private final static String LOGIN_API_ENDPOINT_URL = "/api/v1/sessions.json";
    private String mUserName, mUserPassword;
    TextView forgetPasswordText;
    Button signin_button, signup_button;
    CoordinatorLayout coordinate_login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String device_id = "";
    private ProgressDialog mProgressDialog;
    Activity activity;
    HashMap<String, String> allowedUser = new HashMap<>();
    private RelativeLayout signInLayout;
    private Button registerButton;
    //    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_journey_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        signin_button = (Button) findViewById(R.id.signin_button);
        coordinate_login = (CoordinatorLayout) findViewById(R.id.coordinate_login);
        signInLayout = (RelativeLayout) findViewById(R.id.sign_in_layout);
        registerButton= (Button) findViewById(R.id.registerButton);
        addAllowedUsers();
        activity = this;

        signin_button.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                startActivity(new Intent(LoginJourneyActivity.this,SignUpActivity.class));
                break;
            case R.id.signin_button:
                doLogin(v);
                break;
        }
    }

    private void doLogin(View v) {
        hideKeyboard(v);
        EditText userEmailField = (EditText) findViewById(R.id.signin_email);
        EditText userPasswordField = (EditText) findViewById(R.id.sigin_password);
        mUserName = userEmailField.getText().toString();
        mUserPassword = userPasswordField.getText().toString();

        if (mUserName.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter email address.", Toast.LENGTH_LONG).show();
        } else if (mUserPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter password.", Toast.LENGTH_LONG).show();
        } else {
//                    checkLoginCredential(mUserName, mUserPassword);
            checkLoginUsername(mUserName, mUserPassword);
        }
    }

    private void checkLoginCredential(String mUserName, String mUserPassword) {
        if (allowedUser.containsKey(mUserName.trim())) {
            String passwordValue = allowedUser.get(mUserName.trim());
            if (mUserPassword.trim().equals(passwordValue)) {
                //user success
                saveData(mUserName, "","");
            } else {
                //password is wrong
                Toast.makeText(getApplicationContext(), "Entered password is wrong, Please try again.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "User doesn't exist.", Toast.LENGTH_LONG).show();
        }
    }

    private void addAllowedUsers() {
        allowedUser.put("anand123", "anand123");
        allowedUser.put("gokul123", "gokul123");
        allowedUser.put("arshia123", "arshia123");
        allowedUser.put("mikko", "mikko123");
    }

    private void checkLoginUsername(final String mUserName, final String mUserPassword) {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiClientInterface = APIClient.createService(ApiInterface.class);

        HashMap<String, String> userHashMap = new HashMap<>();
        userHashMap.put("grant_type", "password");
        userHashMap.put("client_id", "mobile_app_login");
        userHashMap.put("username", mUserName);
        userHashMap.put("password", mUserPassword);
//        Call<ResponseBody> loginResponse = apiClientInterface.login(userHashMap);

        try {

            Call<ResponseBody> loginResponse = apiClientInterface.login("password", "mobile_app_login",
                    (mUserName), mUserPassword);
            loginResponse.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressBar.setVisibility(View.GONE);
                    LogUtils.printLog(TAG, "code =" + response.code());
                    try {
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                JSONObject object = new JSONObject(response.body().string());
                                String access_token = object.getString("access_token");
                                LogUtils.printLog(TAG, "access_token =" + access_token);
                                saveData(mUserName, mUserPassword, access_token);
                            }
                        } else {
                            //TODO show error message
                            if (response.errorBody() != null) {
                                JSONObject object = new JSONObject(response.errorBody().string());
                                String message = object.getString("error");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //TODO remove this line from failure
                    progressBar.setVisibility(View.GONE);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveData(String mUserEmail, String mUserPassword, String accessToken) {
        PreferencesManager.putString("isUserLoggedIn", "true");
        PreferencesManager.putString(StringConstants.KEY_USER_EMAIL, mUserEmail);
        Utility.saveJwtToken(accessToken);
        startHomeActivity();
    }

    private void startHomeActivity() {
        startActivity(new Intent(LoginJourneyActivity.this, ModelActivity.class));
        LoginJourneyActivity.this.finish();
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //show progressdialog for google signIn
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    //hide progressdialog for google signIn
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    @SuppressLint("HardwareIds")
    private String get_device_id() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        try {
            if (manager != null) {
                if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                    deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                } else {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    deviceId = manager.getDeviceId();
                    if (deviceId.equals("")) {
                        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    }
                }
            } else {
                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }
}
