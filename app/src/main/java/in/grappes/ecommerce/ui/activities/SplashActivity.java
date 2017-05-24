package in.grappes.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.utils.SharedPrefUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPrefUtils.getCurrentLocation(SplashActivity.this)!=null)
                {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }

                Intent i = new Intent(SplashActivity.this, LocationDetectionActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
