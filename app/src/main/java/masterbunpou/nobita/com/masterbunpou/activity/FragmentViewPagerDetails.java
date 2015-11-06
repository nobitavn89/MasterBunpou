package masterbunpou.nobita.com.masterbunpou.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        //change toolbar menu
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_detail_view, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("nobita", "item click: " + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                Toast.makeText(getActivity(), "This feature is coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
