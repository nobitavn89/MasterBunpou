package masterbunpou.nobita.com.masterbunpou.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.adapter.SmartFragmentStatePagerAdapter;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;
import masterbunpou.nobita.com.masterbunpou.utils.Utils;

/**
 * Created by nobitavn89 on 15/11/04.
 */
public class FragmentViewPager extends Fragment {
    private ViewPager mViewPager;
    SmartDetailViewPagerAdapter mViewPagerAdapter;
    List<CardViewItem> mListData = Collections.emptyList();
    private static String mCardType;
    private int mFirstDisplay;
    public static class SmartDetailViewPagerAdapter extends SmartFragmentStatePagerAdapter<FragmentViewPagerDetails> {
        private int mPageSize;

        public  SmartDetailViewPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mPageSize = size;
        }

        @Override
        public int getCount() {
            return mPageSize;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentViewPagerDetails.newInstance(position,mCardType);
        }
    }


    public  FragmentViewPager() {
        //require empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardType = getArguments().getString(Constants.CARD_DATA_TYPE);
        mFirstDisplay = getArguments().getInt(Constants.VIEW_PAGER_ID);
        mListData = Utils.getData(getActivity(), mCardType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_viewpager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.detail_pager);
//        String cardDataType = getArguments().getString(Constants.CARD_DATA_TYPE);

        int size = Utils.getData(getActivity(), mCardType).size();
        Log.d("nobita", "fragment created, size: " + size);
        mViewPagerAdapter = new SmartDetailViewPagerAdapter(getFragmentManager(), size);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(mFirstDisplay);
        CardViewItem item = mListData.get(mFirstDisplay);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                CardViewItem item = mListData.get(position);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        new setAdapterTask().execute();
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
