package masterbunpou.nobita.com.masterbunpou.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;
import masterbunpou.nobita.com.masterbunpou.utils.Utils;

/**
 * Created by nobitavn89 on 15/11/04.
 * similar to FragmentViewPager, but use to proceed bookmark data. Since
 * we need some different methods, I create a new class
 * Merge later
 */
public class FragmentViewPagerBookmark extends Fragment {
    private ViewPager mViewPager;
    FragmentViewPager.SmartDetailViewPagerAdapter mViewPagerAdapter;
    List<CardViewItem> mListData = Collections.emptyList();
    private static String mCardType;
    private int mFirstDisplay;
    private int mIsBookmark;


    public FragmentViewPagerBookmark() {
        //require empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        mCardType = getArguments().getString(Constants.CARD_DATA_TYPE);
        mFirstDisplay = getArguments().getInt(Constants.VIEW_PAGER_ID);
        mListData = Utils.getData(getActivity(), mCardType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_viewpager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.detail_pager);
//        String cardDataType = getArguments().getString(Constants.CARD_DATA_TYPE);

        int size = Utils.getData(getActivity(), mCardType).size();
        Log.d("nobita", "fragment created, size: " + size);
        mViewPagerAdapter = new FragmentViewPager.SmartDetailViewPagerAdapter(getFragmentManager(), size);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(mFirstDisplay);
        CardViewItem item = mListData.get(mFirstDisplay);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle());
        mIsBookmark = item.getBookmarkState();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                CardViewItem item = mListData.get(position);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle());
                mIsBookmark = item.getBookmarkState();
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //animating the scroll
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();
                int pageHeight = page.getHeight();

                if(position < -1) {
                    //way-off screen to the left
                    page.setAlpha(0);
                } else if (position <=1) {
                    //modify here for pages in view
                } else  {
                    page.setAlpha(0);
                }

            }
        });
//        new setAdapterTask().execute();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_view, menu);
        MenuItem item = menu.findItem(R.id.action_bookmark);
        if(mIsBookmark == 1) {
            item.setIcon(R.drawable.ic_bookmark_black_24dp);
        } else {
            item.setIcon(R.drawable.ic_bookmark_white_24dp);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_bookmark);
        if(mIsBookmark == 1) {
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
                getActivity().onBackPressed();
                return true;
            case R.id.action_bookmark:
                Toast.makeText(getActivity(), "Bookmark for id: "+ mViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                mIsBookmark = (mIsBookmark == 1 ? 0: 1);
                int currentItem = mViewPager.getCurrentItem();
                CardViewItem cardItem = mListData.get(currentItem);
                cardItem.setBookmarkState(mIsBookmark);
                mListData.set(currentItem, cardItem);

                Utils.bookmarkCard(getActivity(), cardItem.getCardId(), mIsBookmark);
                getActivity().invalidateOptionsMenu();
                break;
            default:
                Toast.makeText(getActivity(), "This feature is coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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
