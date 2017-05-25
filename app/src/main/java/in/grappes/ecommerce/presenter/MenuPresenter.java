package in.grappes.ecommerce.presenter;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.HomeDataTo;
import in.grappes.ecommerce.model.Product;

/**
 * Created by gunjit on 06/05/17.
 */

public class MenuPresenter {

    Context context;
    HomeDataInterface homeDataInterface;
    ProductDetailInterface productDetailInterface;
    GetProductsInterface getProductsInterface;


    public MenuPresenter(Context context, HomeDataInterface homeDataInterface) {
        this.context = context;
        this.homeDataInterface = homeDataInterface;
    }

    public interface HomeDataInterface{
        void onGetHomeDataSuccess(HomeDataTo homeDataTo);
        void onGetHomeDataFailure(String error);
    }

    public void fetchHomeData(){
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mFirebaseRef.child("homeDataTo").limitToFirst(1);

        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot homeScreenSnapShot : dataSnapshot.getChildren()) {
                    HomeDataTo homeDataTo = homeScreenSnapShot.getValue(HomeDataTo.class);
                    if(homeDataTo!=null)
                    {
                        homeDataInterface.onGetHomeDataSuccess(homeDataTo);
                    }
                    else
                    {
                        homeDataInterface.onGetHomeDataFailure(context.getResources().getString(R.string.somethingwentwrong));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                homeDataInterface.onGetHomeDataFailure(databaseError.getMessage());
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////


    public MenuPresenter(Context context, ProductDetailInterface productDetailInterface) {
        this.context = context;
        this.productDetailInterface = productDetailInterface;
    }

    public interface ProductDetailInterface{
        void onGetProductDetailSuccess(Product product);
        void onGetProductDetailFailure(String message);
    }

    public void fetchProductDetails(String  productId){

    }

    /////////////////////////////////////////////////////////////////////////////////////

    public MenuPresenter(Context context, GetProductsInterface getProductsInterface) {
        this.context = context;
        this.getProductsInterface = getProductsInterface;
    }

    public interface GetProductsInterface{
        void OnGetAllProductOfCategory(ArrayList<Product> products);
        void OnGetProductDetailFailure(String errorMessage);
    }

    public void fetchAllProductByCategory(String categoryId){
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mFirebaseRef.child("products").orderByChild("categoryCode").equalTo(categoryId);

        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Product> products = new ArrayList<Product>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    products.add(product);
                }
                getProductsInterface.OnGetAllProductOfCategory(products);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError!=null)
                {
                    getProductsInterface.OnGetProductDetailFailure(databaseError.getMessage());
                    return;
                }
                getProductsInterface.OnGetProductDetailFailure(context.getResources().getString(R.string.somethingwentwrong));
            }
        });
    }


}
