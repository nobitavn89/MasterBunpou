package masterbunpou.nobita.com.masterbunpou.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import masterbunpou.nobita.com.masterbunpou.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setText(R.string.under_construction);
        return textView;
    }


}
