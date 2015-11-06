package masterbunpou.nobita.com.masterbunpou.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.adapter.DetailViewPagerAdapter;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;
import masterbunpou.nobita.com.masterbunpou.utils.Utils;

/**
 * Created by nobitavn89 on 15/11/04.
 */
public class FragmentViewPager extends Fragment {
    private ViewPager mViewPager;
    DetailViewPagerAdapter mViewPagerAdapter;
    List<CardViewItem> mListData = Collections.emptyList();

    public  FragmentViewPager() {
        //require empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_viewpager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.detail_pager);
        String cardDataType = getArguments().getString(Constants.CARD_DATA_TYPE);

        int size = Utils.getData(getActivity(), cardDataType).size();
        Log.d("nobita", "fragment created, size: " + size);

        mViewPagerAdapter = new DetailViewPagerAdapter(getFragmentManager(), size, cardDataType);
//        mViewPager.setAdapter(viewPagerAdapter);
//        mViewPager.setCurrentItem(cardId);
        new setAdapterTask().execute();
        return view;
    }

    private class setAdapterTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void results) {
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.setCurrentItem(getArguments().getInt(Constants.VIEW_PAGER_ID));
        }
    }
}
