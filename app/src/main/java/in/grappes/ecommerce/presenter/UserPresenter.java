package in.grappes.ecommerce.presenter;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.grappes.ecommerce.model.User;

/**
 * Created by gunjit on 06/05/17.
 */

public class UserPresenter {
    Context context;
    LoginInterface loginInterface;
    SignupInterface signupInterface;
    CheckUserExistInterface checkUserExistInterface;


    public UserPresenter(Context context, LoginInterface loginInterface) {
        this.context = context;
        this.loginInterface = loginInterface;
    }

    public interface LoginInterface{
        void onLoginSuccess(User user);
        void onLoginFailed(String error);
    }

    public UserPresenter(Context context, SignupInterface signupInterface) {
        this.context = context;
        this.signupInterface = signupInterface;
    }

    public UserPresenter(Context context, CheckUserExistInterface checkUserExistInterface)
    {
        this.context = context;
        this.checkUserExistInterface = checkUserExistInterface;
    }

    public interface SignupInterface{
        void onSignupSuccess(User user);
        void onSignupFailed(String error);
    }


    public interface CheckUserExistInterface{
        void onUserExist(User user);
        void onUserDoesNotExist();
    }

    public void loginUser(String emailAddress, final String password) {

        Log.e("Email-----", ""+emailAddress+" "+password);

        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mFirebaseRef.child("users").orderByChild("email").equalTo(emailAddress);

        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
                    User user = userSnapShot.getValue(User.class);
                    if (user != null && user.password.equals(password)) {
                        loginInterface.onLoginSuccess(user);
                    } else {
                        loginInterface.onLoginFailed("Incorrect combination");
                    }
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loginInterface.onLoginFailed(""+databaseError.getMessage());
            }
        });
    }

    public void signupUser(final User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        String userId = mDatabase.push().getKey();
        mDatabase.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError!=null)
                {
                    signupInterface.onSignupFailed(databaseError.getMessage());
                    return;
                }
                signupInterface.onSignupSuccess(user);
            }
        });
    }

    public void checkIfUserExists(String email)
    {
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mFirebaseRef.child("users").orderByChild("email").equalTo(email);

        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
                    User user = userSnapShot.getValue(User.class);

                    if (user != null) {
                        checkUserExistInterface.onUserExist(user);
                    } else {
                        checkUserExistInterface.onUserDoesNotExist();
                    }
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
