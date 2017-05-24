package in.grappes.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.AddressTo;
import in.grappes.ecommerce.presenter.AddressPresenter;
import in.grappes.ecommerce.ui.adapter.AddressAdapter;
import in.grappes.ecommerce.utils.SharedPrefUtils;

public class AddressActivity extends AppCompatActivity implements Validator.ValidationListener {

    LinearLayout addressFormLayout;
    RelativeLayout addressListLayout;

    RecyclerView addressListView;

    @NotEmpty
    EditText addressMain;
    @NotEmpty
    EditText addressLocality;
    @NotEmpty
    EditText addressDeliveryInstruction;
    TextView confirmAddressButton;

    TextView addNewAddressButton;

    Validator validator;

    @NotEmpty
    EditText customerName;
    @NotEmpty
    EditText customerPhone;

    SweetAlertDialog pDialog;

    private AddressAdapter addressListAdapter ;
    ArrayList<AddressTo> addressToArrayList = new ArrayList<>();
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Select an address");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setViews();
        initViews();
        setAdapter();
        setRecyclerView();

    }

    private void setAdapter() {
        addressListAdapter = new AddressAdapter(AddressActivity.this, addressToArrayList);
    }

    private void setRecyclerView() {
        llm = new LinearLayoutManager(AddressActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        addressListView.setLayoutManager(llm);
        addressListView.setHasFixedSize(false);
        addressListView.setAdapter(addressListAdapter);
    }

    private void initViews() {
        addNewAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressForm();
            }
        });

        try {
            customerName.setText(SharedPrefUtils.getCurrentUser(AddressActivity.this).firstName);
            customerPhone.setText(String.valueOf(SharedPrefUtils.getCurrentUser(AddressActivity.this).mobileNumber));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        confirmAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        getUserAddresses(SharedPrefUtils.getCurrentUser(AddressActivity.this).email);
    }

    private void setViews() {
        addressFormLayout = (LinearLayout) findViewById(R.id.addressFormLayout);
        addressListLayout = (RelativeLayout) findViewById(R.id.addressListLayout);

        addNewAddressButton = (TextView) findViewById(R.id.addNewAddressButton);


        addressListView = (RecyclerView) findViewById(R.id.addressListView);

        addressMain = (EditText) findViewById(R.id.addressLine1);
        addressLocality = (EditText) findViewById(R.id.addressLocality);
        addressDeliveryInstruction = (EditText) findViewById(R.id.addressInstruction);

        customerName = (EditText) findViewById(R.id.customerAddressName);
        customerPhone = (EditText) findViewById(R.id.customerAddressPhone);

        confirmAddressButton = (TextView) findViewById(R.id.addressSaveButton);

    }

    public void showAddressList()
    {
        addressFormLayout.setVisibility(View.GONE);
        addressListLayout.setVisibility(View.VISIBLE);
    }

    public void showAddressForm()
    {
        addressFormLayout.setVisibility(View.VISIBLE);
        addressListLayout.setVisibility(View.GONE);
    }

    private void getUserAddresses(String email) {
        AddressActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressBar();
            }
        });
        new AddressPresenter(AddressActivity.this, getUserAddressesInterface).getAllAddressesOfUser(SharedPrefUtils.getCurrentUser(AddressActivity.this).email);
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
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onValidationSucceeded() {
        saveAddressToBackend();
    }

    private void saveAddressToBackend() {
        AddressActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressBar();
            }
        });

        AddressTo addressTo = new AddressTo();
        addressTo.houseNumber = addressMain.getText().toString();
        addressTo.locality = addressLocality.getText().toString();
        try {
            addressTo.deliveryInstructions = addressDeliveryInstruction.getText().toString();
        }
        catch (Exception e)
        {
            addressTo.deliveryInstructions = "";
        }
        addressTo.customerName = SharedPrefUtils.getCurrentUser(AddressActivity.this).firstName;
        addressTo.customerPhone = SharedPrefUtils.getCurrentUser(AddressActivity.this).mobileNumber;
        addressTo.linkedEmail = SharedPrefUtils.getCurrentUser(AddressActivity.this).email;

        new AddressPresenter(AddressActivity.this, addAddressInterface).addAddress(addressTo);

    }

    public void moveToNextActivity(AddressTo deliveryAddress) {
        Intent i = new Intent(AddressActivity.this, CheckoutActivity.class);
        i.putExtra("address",deliveryAddress);

        startActivity(i);
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
                Toast.makeText(AddressActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showProgressBar()
    {
        try {
            pDialog.setTitleText("Loading");
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

    AddressPresenter.AddAddressInterface addAddressInterface = new AddressPresenter.AddAddressInterface() {
        @Override
        public void onAddAddressSuccess(AddressTo addressTo) {
            hideProgressBar();
            moveToNextActivity(addressTo);
        }

        @Override
        public void onAddAddressFailure(String errorMsg) {
            Toast.makeText(AddressActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    AddressPresenter.GetUserAddressesInterface getUserAddressesInterface = new AddressPresenter.GetUserAddressesInterface() {
        @Override
        public void onFetchAllAddressesSuccess(ArrayList<AddressTo> addressTos) {
            hideProgressBar();

            if (addressTos.size() > 0) {
                AddressActivity.this.addressToArrayList.clear();
                AddressActivity.this.addressToArrayList.addAll(addressTos);
                addressListAdapter.notifyDataSetChanged();
                showAddressList();
                Log.e("onFetchAllAddresses", ""+new Gson().toJson(addressToArrayList));
            }
            else
            {
                showAddressForm();
            }
        }

        @Override
        public void onAddAddressFailure(String errorMsg) {
            hideProgressBar();
            showAddressForm();
        }
    };
}
