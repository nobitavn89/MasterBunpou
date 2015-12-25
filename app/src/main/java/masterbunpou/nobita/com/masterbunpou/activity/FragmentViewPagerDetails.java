package masterbunpou.nobita.com.masterbunpou.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;
import masterbunpou.nobita.com.masterbunpou.utils.Utils;

/**
 * nobitavn89
 * used by viewpager
 */
public class FragmentViewPagerDetails extends Fragment {
    private String mCardDataType;
    private int mPageId;
//    private  List<CardViewItem> mListData;

    public static FragmentViewPagerDetails newInstance(int pageId, String cardType) {
        Log.d("nobita", "new instance for page: " +pageId);
        FragmentViewPagerDetails fragment = new FragmentViewPagerDetails();
        Bundle args = new Bundle();
        args.putString(Constants.CARD_DATA_TYPE, cardType);
        args.putInt(Constants.DETAIL_VIEW_PAGER_ID, pageId);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentViewPagerDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardDataType = getArguments().getString(Constants.CARD_DATA_TYPE, Constants.CARD_TYPE_JLPT_N3);
        mPageId = getArguments().getInt(Constants.DETAIL_VIEW_PAGER_ID, 0);
        Log.d("nobita", "oncreate, card_data: " + mCardDataType + ", id: " + mPageId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        mCardDataType = getArguments().getString(Constants.CARD_DATA_TYPE);
//        mPageId = getArguments().getInt(Constants.VIEW_PAGER_ID);
        Log.d("nobita", "oncreateView, card_data: " + mCardDataType + ", id: " + mPageId);
        CardViewItem item = Utils.getData(getActivity(), mCardDataType).get(mPageId);
        Log.d("nobita", "fragment title: " + item.getTitle());
        View layout = inflater.inflate(R.layout.pagerview_item, container, false);
        TextView txtTitle = (TextView)layout.findViewById(R.id.txt_pager_title);
        TextView txtMeaning = (TextView) layout.findViewById(R.id.txt_viewpager_meaning);
        TextView txtUsage = (TextView) layout.findViewById(R.id.txt_viewpager_usage);
        TextView txtEx = (TextView) layout.findViewById(R.id.txt_viewpager_ex);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle());

        txtTitle.setText(item.getTitle());
        txtMeaning.setText(item.getMeaning());
        txtUsage.setText(item.getUsage());
        txtEx.setText(item.getEx());

        return layout;
    }

}
