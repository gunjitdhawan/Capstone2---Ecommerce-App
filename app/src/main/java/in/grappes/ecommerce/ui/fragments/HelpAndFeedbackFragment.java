package in.grappes.ecommerce.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.grappes.ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpAndFeedbackFragment extends Fragment {

    TextView callUsButton;
    TextView emailUsButton;

    public HelpAndFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_and_feedback, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        callUsButton = (TextView) view.findViewById(R.id.callUsButton);
        emailUsButton = (TextView) view.findViewById(R.id.emailUsButton);

        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9999999999"));
                startActivity(intent);
            }
        });

        emailUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "support@udacity-gunjit.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);


            }
        });
    }

}
