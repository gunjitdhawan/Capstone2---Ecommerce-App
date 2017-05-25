package in.grappes.ecommerce.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.HomeDataTo;
import in.grappes.ecommerce.presenter.MenuPresenter;
import in.grappes.ecommerce.ui.adapter.HomeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    Context context;
    HomeDataTo homeDataTo = new HomeDataTo();
    ProgressBar progressBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        setViews(view);
        initViews();
        getHomeScreenData();

        return view;
    }

    private void getHomeScreenData() {
        new MenuPresenter(context, homeDataInterface).fetchHomeData();
    }

    private void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(context);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(homeAdapter);
    }

    private void setAdapter() {
        homeAdapter = new HomeAdapter(context, homeDataTo);
    }

    private void initViews() {

    }

    private void setViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_dialog);
    }

    MenuPresenter.HomeDataInterface homeDataInterface = new MenuPresenter.HomeDataInterface() {
        @Override
        public void onGetHomeDataSuccess(HomeDataTo homeDataTo) {
            progressBar.setVisibility(View.GONE);
            HomeFragment.this.homeDataTo = homeDataTo;
            setAdapter();
            setupRecyclerView();

            homeAdapter.dealOfDayDataBinder.rcAdapter.performClick();
        }

        @Override
        public void onGetHomeDataFailure(String errorMessage) {
            progressBar.setVisibility(View.GONE);

            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

}
