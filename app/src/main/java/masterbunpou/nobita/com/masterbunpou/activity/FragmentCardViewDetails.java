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

import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.data.DataManager;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * nobitavn89
 */
public class FragmentCardViewDetails extends Fragment {
    private String mCardDataType;
    private int mStartId;
    private  List<CardViewItem> mListData;

    public FragmentCardViewDetails() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCardDataType = getArguments().getString(Constants.CARD_DATA_TYPE);
        mStartId = getArguments().getInt(Constants.CARD_ID);
        mListData = getData();
        CardViewItem item = mListData.get(mStartId);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle());
        View layout = inflater.inflate(R.layout.pagerview_item, container, false);
        TextView txtTitle = (TextView)layout.findViewById(R.id.txt_pager_title);
        TextView txtMeaning = (TextView) layout.findViewById(R.id.txt_viewpager_meaning);
        TextView txtUsage = (TextView) layout.findViewById(R.id.txt_viewpager_usage);
        TextView txtEx = (TextView) layout.findViewById(R.id.txt_viewpager_ex);

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

    public List<CardViewItem> getData() {
        List<CardViewItem> list = DataManager.getInstance().getData(getActivity(), mCardDataType);
        Log.d("detailFragment", "getData, size: " + list.size());
        return list;
    }



}
