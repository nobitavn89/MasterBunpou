package masterbunpou.nobita.com.masterbunpou.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import masterbunpou.nobita.com.masterbunpou.activity.FragmentViewPagerDetails;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/11/04.
 * @deprecated
 */
public class DetailViewPagerAdapter extends FragmentPagerAdapter{
    private int mPageSize;
    private String mCardType;

    public  DetailViewPagerAdapter(FragmentManager fm, int size, String cardType) {
        super(fm);
        mPageSize = size;
        mCardType = cardType;
    }

    @Override
    public int getCount() {
        return mPageSize;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("nobita", "adapter get Item: " + position + ", pageSize: " + mPageSize);
        FragmentViewPagerDetails fragment = new FragmentViewPagerDetails();
        Bundle data = new Bundle();
        data.putString(Constants.CARD_DATA_TYPE, mCardType);
        data.putInt(Constants.VIEW_PAGER_ID, position);
        fragment.setArguments(data);
        return fragment;
    }
}
