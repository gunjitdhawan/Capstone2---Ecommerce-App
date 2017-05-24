package in.grappes.ecommerce.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.grappes.ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermAndConditionFragment extends Fragment {

    public TermAndConditionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_term_and_condition, container, false);
    }

}
