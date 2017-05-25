package in.grappes.ecommerce.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.User;
import in.grappes.ecommerce.presenter.UserPresenter;
import in.grappes.ecommerce.utils.SharedPrefUtils;
import io.fabric.sdk.android.Fabric;

import static in.grappes.ecommerce.utils.AppConstants.TWITTER_KEY;
import static in.grappes.ecommerce.utils.AppConstants.TWITTER_SECRET;

public class SignupActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    EditText fullName;
    @NotEmpty
    EditText emailAddress;
    @NotEmpty
    EditText mobileNumber;
    @NotEmpty
    @Password(min = 5)
    EditText password;
    @NotEmpty
    @ConfirmPassword
    EditText confirmPassword;

    int verCode = 0;

    Button signUpButton;

    LinearLayout signUpLayout;
    RelativeLayout otpLayout;

    TextView gotoLoginButton;

    Validator validator;

    String destination;

    SweetAlertDialog pDialog;

    AuthCallback authCallback;
    DigitsAuthButton digitsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setCallBack();
        setContentView(R.layout.activity_signup);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getResources().getString(R.string.signup));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        destination = i.getStringExtra("destination");

        fullName = (EditText) findViewById(R.id.fullName);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        signUpLayout = (LinearLayout) findViewById(R.id.signUpLayout);
        otpLayout = (RelativeLayout) findViewById(R.id.otpLayout);

        signUpLayout.setVisibility(View.VISIBLE);
        otpLayout.setVisibility(View.GONE);

        validator = new Validator(this);
        validator.setValidationListener(this);

        gotoLoginButton = (TextView) findViewById(R.id.gotoLoginButton);
        gotoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                i.putExtra("destination",destination);
                startActivity(i);
                finish();
            }
        });



        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                validator.validate();
            }
        });



        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(authCallback);

    }



    private void setCallBack() {
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                uploadUserToFirebase();
            }

            @Override
            public void failure(DigitsException exception) {
                Log.e("OTP VERIFICATION FAILED", ""+exception.getLocalizedMessage());
            }
        };
    }

    public void verifyOTP() {
        otpLayout.setVisibility(View.VISIBLE);
        signUpLayout.setVisibility(View.GONE);

        AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                .withAuthCallBack(authCallback)
                .withPhoneNumber(getResources().getString(R.string.phoncode)+mobileNumber.getText().toString());

        Digits.authenticate(authConfigBuilder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onValidationSucceeded() {
        //verifyOTP();
        checkIfEmailIdExists();
    }

    private void checkIfEmailIdExists() {
        SignupActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressBar();
            }
        });
        new UserPresenter(SignupActivity.this, checkUserExistInterface).checkIfUserExists(emailAddress.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


    private void uploadUserToFirebase() {


        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            verCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SignupActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressBar();
            }
        });

        User user = new User();
        user.email = emailAddress.getText().toString().toLowerCase().trim();
        user.firstName = fullName.getText().toString().trim();
        user.mobileNumber = mobileNumber.getText().toString().trim();
        user.androidVersion = ""+Build.VERSION.SDK_INT;
        user.deviceInfo = Build.MANUFACTURER + " - " + Build.MODEL;
        user.appVersion = ""+verCode;
        user.password = password.getText().toString();
        user.imei = getImei();

        new UserPresenter(SignupActivity.this, userSignupInterface).signupUser(user);
    }

    private String getImei() {
        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return mngr.getDeviceId();

    }

    private void moveToNextActivity() {
        if(destination.equalsIgnoreCase("checkout"))
        {
            Intent i = new Intent(SignupActivity.this,AddressActivity.class);
            startActivity(i);
            finish();
        }
        else if(destination.equalsIgnoreCase("home"))
        {
            finish();
        }
        else
        {
            Intent i = new Intent(SignupActivity.this, HomeActivity.class);
            i.putExtra("openMyOrders", true);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        if(otpLayout.getVisibility() == View.VISIBLE)
        {
            signUpLayout.setVisibility(View.VISIBLE);
            otpLayout.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }
    public void showProgressBar()
    {
        try {

            pDialog.setTitleText(getResources().getString(R.string.loading));
            pDialog.setCancelable(false);
            pDialog.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void hideProgressBar()
    {
        try {
            pDialog.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    UserPresenter.SignupInterface userSignupInterface = new UserPresenter.SignupInterface() {
        @Override
        public void onSignupSuccess(User user) {
            SharedPrefUtils.setCurrentUser(SignupActivity.this, user);
            hideProgressBar();
            moveToNextActivity();
        }

        @Override
        public void onSignupFailed(String error) {
            hideProgressBar();
        }
    };

    UserPresenter.CheckUserExistInterface checkUserExistInterface = new UserPresenter.CheckUserExistInterface() {
        @Override
        public void onUserExist(User user) {
            Toast.makeText(SignupActivity.this, getResources().getString(R.string.already_exist), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUserDoesNotExist() {
            verifyOTP();
        }
    };

}
