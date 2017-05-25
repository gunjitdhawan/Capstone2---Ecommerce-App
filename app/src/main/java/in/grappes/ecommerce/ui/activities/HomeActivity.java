package in.grappes.ecommerce.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Cart;
import in.grappes.ecommerce.model.Product;
import in.grappes.ecommerce.ui.fragments.HelpAndFeedbackFragment;
import in.grappes.ecommerce.ui.fragments.HomeFragment;
import in.grappes.ecommerce.ui.fragments.MyOrderFragment;
import in.grappes.ecommerce.ui.fragments.TermAndConditionFragment;
import in.grappes.ecommerce.utils.AppConstants;
import in.grappes.ecommerce.utils.AppUtils;
import in.grappes.ecommerce.utils.SharedPrefUtils;
import in.grappes.ecommerce.utils.ToolbarUtils;

public class HomeActivity extends AppCompatActivity implements Cart.CartUpdateListener{

    public DrawerLayout drawerLayout;
    FragmentManager fragmentManager;

    HomeFragment homeFragment;
    MyOrderFragment myOrderFragment;
    HelpAndFeedbackFragment helpAndFeedbackFragment;
    TermAndConditionFragment termAndConditionFragment;
    TextView userNameView;
    TextView userDescView;
    boolean openMyOrders = false;
    public boolean openPopularProduct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ToolbarUtils.setToolbar((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)), HomeActivity.this, "HOME");

        getIntentData();
        initNavigationDrawer();
        //sendData();
    }

    private void getIntentData() {
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            openMyOrders = getIntent().getExtras().getBoolean("openMyOrders");
            openPopularProduct = getIntent().getExtras().getBoolean("openPopularProduct");
        }
    }

    private void sendData() {

        for(int i=1; i<=30;i++) {
            Product product = new Product();
            product.productName = "Veg Item #"+i;
            product.productDescription = "However venture pursuit he am mr cordial. Forming musical am hearing studied be luckily. Ourselves for determine attending how led gentleman sincerity. Valley afford uneasy joy she thrown though bed set. In me forming general prudent on country carried. Behaved an or suppose justice. Seemed whence how son rather easily and change missed. Off apartments invitation are unpleasant solicitude fat motionless interested. Hardly suffer wisdom wishes valley as an. As friendship advantages resolution it alteration stimulated he or increasing. ";
            product.shortDescription = "This is a random short description "+i;
            product.category = "Vegetarian";
            product.categoryCode = "veg";
            product.productImageLink = "http://www.healthyfood.co.uk/wp-content/uploads/2015/02/veg.jpg";
            product.imageLinkArray = new ArrayList<>();
            product.sellingPrice = i;
            product.originalPrice = i;

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
            String id = mDatabase.push().getKey();
            final int finalI = i;
            mDatabase.child(id).setValue(product, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e("error", "" + databaseError.getDetails());
                        return;
                    }
                    Log.e("success", "-- "+ finalI);
                }
            });
        }


        for(int i=1; i<=30;i++) {
            Product product = new Product();
            product.productName = "Non-Veg Item #"+i;
            product.productDescription = "However venture pursuit he am mr cordial. Forming musical am hearing studied be luckily. Ourselves for determine attending how led gentleman sincerity. Valley afford uneasy joy she thrown though bed set. In me forming general prudent on country carried. Behaved an or suppose justice. Seemed whence how son rather easily and change missed. Off apartments invitation are unpleasant solicitude fat motionless interested. Hardly suffer wisdom wishes valley as an. As friendship advantages resolution it alteration stimulated he or increasing. ";
            product.shortDescription = "This is a random short description "+i;
            product.category = "Non-Vegetarian";
            product.categoryCode = "non-veg";
            product.productImageLink = "http://www.rushhrs.com/img/non-veg.png";
            product.imageLinkArray = new ArrayList<>();
            product.sellingPrice = i;
            product.originalPrice = i;

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
            String id = mDatabase.push().getKey();
            final int finalI = i;
            mDatabase.child(id).setValue(product, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e("error", "" + databaseError.getDetails());
                        return;
                    }
                    Log.e("success", "-- "+ finalI);
                }
            });
        }

        for(int i=1; i<=30;i++) {
            Product product = new Product();
            product.productName = "Drinks Item #"+i;
            product.productDescription = "However venture pursuit he am mr cordial. Forming musical am hearing studied be luckily. Ourselves for determine attending how led gentleman sincerity. Valley afford uneasy joy she thrown though bed set. In me forming general prudent on country carried. Behaved an or suppose justice. Seemed whence how son rather easily and change missed. Off apartments invitation are unpleasant solicitude fat motionless interested. Hardly suffer wisdom wishes valley as an. As friendship advantages resolution it alteration stimulated he or increasing. ";
            product.shortDescription = "This is a random short description "+i;
            product.category = "Drinks";
            product.categoryCode = "bev";
            product.productImageLink = "http://www.myiconfinder.com/uploads/iconsets/256-256-3cc6fb119766073f832733bdb3b375b7.png";
            product.imageLinkArray = new ArrayList<>();
            product.sellingPrice = i;
            product.originalPrice = i;

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
            String id = mDatabase.push().getKey();
            final int finalI = i;
            mDatabase.child(id).setValue(product, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e("error", "" + databaseError.getDetails());
                        return;
                    }
                    Log.e("success", "-- "+ finalI);
                }
            });
        }

    }

    public void initNavigationDrawer() {

        homeFragment = new HomeFragment();
        myOrderFragment = new MyOrderFragment();

        fragmentManager = getSupportFragmentManager();

        if(openMyOrders)
        {
            fragmentManager.beginTransaction()
                    .add(R.id.container, myOrderFragment, "MOF")
                    .commit();
        }
        else
        {
            fragmentManager.beginTransaction()
                    .add(R.id.container, homeFragment, "HF")
                    .commit();
        }
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home_btn:

                        if(homeFragment == null)
                        {
                            homeFragment = new HomeFragment();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.container, homeFragment, "HF")
                                .commit();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.my_orders:

                        if(SharedPrefUtils.getCurrentUser(HomeActivity.this)==null)
                        {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            intent.putExtra("destination", "myOrders");
                            startActivityForResult(intent, AppConstants.LOGIN);
                            drawerLayout.closeDrawer(Gravity.START);
                            break;
                        }

                        if(myOrderFragment == null)
                        {
                            myOrderFragment = new MyOrderFragment();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.container, myOrderFragment, "MOF")
                                .commit();
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.share_with_friends_btn:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text));
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;

                    case R.id.update_app_btn:
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.help_n_feedback_btn:
                        if(helpAndFeedbackFragment == null)
                        {
                            helpAndFeedbackFragment = new HelpAndFeedbackFragment();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.container, helpAndFeedbackFragment, "HAP")
                                .commit();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.term_n_condition_btn:

                        if(termAndConditionFragment == null)
                        {
                            termAndConditionFragment = new TermAndConditionFragment();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.container, termAndConditionFragment, "TNC")
                                .commit();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.log_out_btn:

                        AppUtils.logout(HomeActivity.this);
                        drawerLayout.closeDrawers();
                        break;

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        userNameView = (TextView)header.findViewById(R.id.drawer_user_name);
        userDescView = (TextView)header.findViewById(R.id.drawer_user_desc);
        TextView editProfileBtn = (TextView)header.findViewById(R.id.drawer_edit_profile_btn);
        ImageView userImageView = (ImageView) header.findViewById(R.id.drawer_user_img);

        if(SharedPrefUtils.getCurrentUser(HomeActivity.this)==null)
        {
            userNameView.setText(getResources().getString(R.string.login_signup));
            userDescView.setText(getResources().getString(R.string.to_view_this));
        }
        else
        {
            userNameView.setText(SharedPrefUtils.getCurrentUser(HomeActivity.this).firstName);
            userDescView.setText(SharedPrefUtils.getCurrentUser(HomeActivity.this).email);
        }

        userNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });

        userDescView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });

        userImageView.setImageResource(R.drawable.ic_launcher);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void startLoginActivity() {
        if(SharedPrefUtils.getCurrentUser(HomeActivity.this)==null)
        {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.putExtra("destination", "home");
            startActivityForResult(intent, AppConstants.LOGIN);
            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(homeFragment.isVisible())
        {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            this.startActivity(i);
        }
        else if(myOrderFragment!=null && myOrderFragment.isVisible())
        {
            if(homeFragment == null)
            {
                homeFragment = new HomeFragment();
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.container, homeFragment, "HF")
                    .commit();
        }
        else if(helpAndFeedbackFragment!=null && helpAndFeedbackFragment.isVisible())
        {
            if(homeFragment == null)
            {
                homeFragment = new HomeFragment();
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.container, homeFragment, "HF")
                    .commit();
        }
        else if(termAndConditionFragment!=null && termAndConditionFragment.isVisible())
        {
            if(homeFragment == null)
            {
                homeFragment = new HomeFragment();
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.container, homeFragment, "HF")
                    .commit();
        }
        else
        {
            finish();
        }
    }

    @Override
    public void OnCartUpdated() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefUtils.getCurrentUser(HomeActivity.this) != null) {
            userNameView.setText(SharedPrefUtils.getCurrentUser(HomeActivity.this).firstName);
            userDescView.setText(SharedPrefUtils.getCurrentUser(HomeActivity.this).email);
        }
    }
}
