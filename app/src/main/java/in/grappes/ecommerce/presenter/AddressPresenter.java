package in.grappes.ecommerce.presenter;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.AddressTo;

/**
 * Created by gunjit on 06/05/17.
 */

public class AddressPresenter {

    AddAddressInterface addAddressInterface;
    GetUserAddressesInterface getUserAddressesInterface;
    Context context;

    public AddressPresenter(Context context, AddAddressInterface addAddressInterface)
    {
        this.context = context;
        this.addAddressInterface = addAddressInterface;
    }

    public interface AddAddressInterface{
        void onAddAddressSuccess(AddressTo addressTo);
        void onAddAddressFailure(String errorMsg);
    }

    public void addAddress(final AddressTo addressTo)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("addresses");
        String userId = mDatabase.push().getKey();
        mDatabase.child(userId).setValue(addressTo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError!=null)
                {
                    addAddressInterface.onAddAddressFailure(databaseError.getMessage());
                    return;
                }
                addAddressInterface.onAddAddressSuccess(addressTo);
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////

    public AddressPresenter(Context context, GetUserAddressesInterface getUserAddressesInterface)
    {
        this.context = context;
        this.getUserAddressesInterface = getUserAddressesInterface;
    }

    public interface GetUserAddressesInterface{
        void onFetchAllAddressesSuccess(ArrayList<AddressTo> addressTos);
        void onAddAddressFailure(String errorMsg);
    }

    public void getAllAddressesOfUser(String email)
    {
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mFirebaseRef.child("addresses").orderByChild("linkedEmail").equalTo(email);

        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("onDataChange",""+new Gson().toJson(dataSnapshot.getChildren()));
                ArrayList<AddressTo> addressTos = new ArrayList<AddressTo>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    AddressTo addressTo = productSnapshot.getValue(AddressTo.class);
                    addressTos.add(addressTo);
                }
                getUserAddressesInterface.onFetchAllAddressesSuccess(addressTos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError!=null)
                {
                    getUserAddressesInterface.onAddAddressFailure(databaseError.getMessage());
                    return;
                }
                getUserAddressesInterface.onAddAddressFailure(context.getResources().getString(R.string.somethingwentwrong));
            }
        });
    }
}
