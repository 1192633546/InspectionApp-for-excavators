package com.inspectionsheetdemo.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.thirdspaceauto.akka.hacksprint.R;
import eu.thirdspaceauto.akka.hacksprint.Utils.LogUtils;
import eu.thirdspaceauto.akka.hacksprint.network.APIClient;
import eu.thirdspaceauto.akka.hacksprint.network.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private String mUserName, mUserEmail, mUserPassword, mUserConfirmPassword;
    String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.coordinate_signup)
    CoordinatorLayout coordinate_signup;

    @BindView(R.id.name)
    EditText userNameField;
    @BindView(R.id.email)
    EditText userEmailField;
    @BindView(R.id.password)
    EditText userPasswordField;
    @BindView(R.id.rePassword)
    EditText userConfirmPasswordField;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        registerButton.setOnClickListener(this);

        toolbar.setTitle("Register");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                performSignIn(v);
                break;
        }
    }

    private void performSignIn(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

//        mUserName = userNameField.getText().toString();
        mUserName = "";
        mUserEmail = userEmailField.getText().toString();
        mUserPassword = userPasswordField.getText().toString();
        mUserConfirmPassword = userConfirmPasswordField.getText().toString();

        if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
            Snackbar snack = Snackbar.make(coordinate_signup, "Please fill all fields", Snackbar.LENGTH_LONG);
            snack.show();
            return;
        }
        if (!mUserEmail.contains("@") || mUserEmail.indexOf('@') >= mUserEmail.lastIndexOf('.')) {
            Snackbar snack = Snackbar.make(coordinate_signup, "Email is not valid!!", Snackbar.LENGTH_LONG);
            snack.show();
            return;
        }
        if (mUserPassword.length() < 4) {
            Snackbar snack = Snackbar.make(coordinate_signup, "Password is too short. The minimum length required is 4 characters long.", Snackbar.LENGTH_LONG);
            snack.show();
            return;
        }
        if (!(mUserPassword.equals(mUserConfirmPassword))) {
            Snackbar snack = Snackbar.make(coordinate_signup, "Password fields do not match", Snackbar.LENGTH_LONG);
            snack.show();
            return;
        }

//        if (mUserName.length() == 0) {
//            Snackbar snack = Snackbar.make(coordinate_signup, "Please enter your name", Snackbar.LENGTH_LONG);
//            snack.show();
//            return;
//        }

        doSignUp(mUserEmail,mUserPassword);
    }

    private void doSignUp(final String mUserEmail, final String mUserPassword) {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiClientInterface = APIClient.createService(ApiInterface.class);

        HashMap<String, String> userHashMap = new HashMap<>();
        userHashMap.put("email", mUserEmail);
        userHashMap.put("password", mUserPassword);
        Call<ResponseBody> loginResponse = apiClientInterface.register(userHashMap);
        loginResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                LogUtils.printLog(TAG, "code =" + response.code());
                try {
                    if (response.code() == 200 || response.code() == 201) {
                        if(response.body()!=null) {
                            Toast.makeText(getApplicationContext(),"Signup success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this,LoginJourneyActivity.class));
                            SignUpActivity.this.finish();
                        }
                    } else {
                        //TODO show error message
                        if (response.errorBody() != null) {
                            Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //TODO remove this line from failure
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Some error occured, try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(SignUpActivity.this,LoginJourneyActivity.class));
    }
}
