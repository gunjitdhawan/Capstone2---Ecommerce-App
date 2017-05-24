package in.grappes.ecommerce.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.User;
import in.grappes.ecommerce.presenter.UserPresenter;
import in.grappes.ecommerce.utils.SharedPrefUtils;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    EditText emailAddress;
    @NotEmpty
    @Password(min = 5)
    EditText password;

    View loginBtn;
    View gotoSignupBtn;
    String destination;

    Validator validator;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getIntentData();
        setViews();
        initViews();
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void getIntentData() {
        destination = getIntent().getExtras().getString("destination");
    }

    private void initViews() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                validator.validate();
            }
        });

        gotoSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                i.putExtra("destination", destination);
                startActivity(i);
            }
        });
    }

    private void setViews() {
        emailAddress = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);
        gotoSignupBtn = findViewById(R.id.go_to_signup);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void onValidationSucceeded() {
        progressBar.setVisibility(View.VISIBLE);
        new UserPresenter(LoginActivity.this, loginInterface).loginUser(emailAddress.getText().toString(), password.getText().toString());
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

    UserPresenter.LoginInterface loginInterface = new UserPresenter.LoginInterface() {
        @Override
        public void onLoginSuccess(User user) {
            Log.e("Login Success", ""+new Gson().toJson(user));
            progressBar.setVisibility(View.VISIBLE);
            SharedPrefUtils.setCurrentUser(LoginActivity.this, user);
            proceedToNextActivity();
        }

        @Override
        public void onLoginFailed(String error) {
            progressBar.setVisibility(View.VISIBLE);
            Log.e("Login Failed", ""+new Gson().toJson(error));
            Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
        }
    };

    private void proceedToNextActivity() {
        if(destination.equalsIgnoreCase("checkout"))
        {
            Intent intent = new Intent(LoginActivity.this, AddressActivity.class);
            startActivity(intent);
        }
        else if(destination.equalsIgnoreCase("home"))
        {
            finish();
        }
        else
        {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.putExtra("openMyOrders", true);
            startActivity(i);
        }
    }
}
