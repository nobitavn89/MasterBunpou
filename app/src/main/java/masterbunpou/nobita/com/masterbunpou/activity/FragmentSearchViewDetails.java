package masterbunpou.nobita.com.masterbunpou.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;
import masterbunpou.nobita.com.masterbunpou.utils.Utils;

/**
 * nobitavn89
 * used by search
 */
public class FragmentSearchViewDetails extends Fragment {
    private int mIsBookMark;
    private int mId; //ID of this card in DB
    public FragmentSearchViewDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //change toolbar menu
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.search_toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        setHasOptionsMenu(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_back_white_24dp));
        //getdate from arguments
        Bundle bundle = getArguments();
        mId = bundle.getInt(Constants.CARD_VIEW_DETAIL_ID);
        mIsBookMark = bundle.getInt(Constants.CARD_VIEW_DETAIL_BOOKMARK_STATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getdata
        Bundle bundle = getArguments();
        String title = bundle.getString(Constants.CARD_VIEW_DETAIL_TITLE);
        String meaning = bundle.getString(Constants.CARD_VIEW_DETAIL_MEANING);
        String usage = bundle.getString(Constants.CARD_VIEW_DETAIL_USAGE);
        String example = bundle.getString(Constants.CARD_VIEW_DETAIL_EXAMPLE);
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        View layout = inflater.inflate(R.layout.pagerview_item, container, false);
        TextView txtTitle = (TextView)layout.findViewById(R.id.txt_pager_title);
        TextView txtMeaning = (TextView) layout.findViewById(R.id.txt_viewpager_meaning);
        TextView txtUsage = (TextView) layout.findViewById(R.id.txt_viewpager_usage);
        TextView txtEx = (TextView) layout.findViewById(R.id.txt_viewpager_ex);

        txtTitle.setText(title);
        txtMeaning.setText(meaning);
        txtUsage.setText(usage);
        txtEx.setText(example);

        return layout;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_view, menu);
        MenuItem item = menu.findItem(R.id.action_bookmark);
        if(mIsBookMark == 1) {
            item.setIcon(R.drawable.ic_bookmark_black_24dp);
        } else {
            item.setIcon(R.drawable.ic_bookmark_white_24dp);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_bookmark);
        if(mIsBookMark == 1) {
            item.setIcon(R.drawable.ic_bookmark_black_24dp);
        } else {
            item.setIcon(R.drawable.ic_bookmark_white_24dp);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("nobita", "item click: " + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
//                getActivity().onBackPressed();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                return true;
            case R.id.action_bookmark:
                mIsBookMark = (mIsBookMark == 1 ? 0: 1);
                Utils.bookmarkCard(getActivity(), mId, mIsBookMark);
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
