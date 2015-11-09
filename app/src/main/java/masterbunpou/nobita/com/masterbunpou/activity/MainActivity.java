package masterbunpou.nobita.com.masterbunpou.activity;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.data.BunpouEntry;
import masterbunpou.nobita.com.masterbunpou.data.DataManager;
import masterbunpou.nobita.com.masterbunpou.data.MasterBunpouDbHelper;
import masterbunpou.nobita.com.masterbunpou.listener.FragmentItemClickListener;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/*
@author: nobitavn89
 */

public class MainActivity extends AppCompatActivity implements FragmentNaviDrawer.FragmentDrawerListener, FragmentItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentNaviDrawer mDrawerFragment;

    private String mCurrentDisplay; //card_view or detail_view
    private String mCurrentCardData;    //
    private int mCardID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "dcm");
//            }
//        });

        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerFragment = (FragmentNaviDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navi_drawer);
        mDrawerFragment.setUp(R.id.fragment_navi_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mDrawerFragment.setDrawerListener(this);

        //test
        setIntent(readHistory());
        changeView();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        File dataBase = this.getDatabasePath(Constants.DATABASE_NAME);
//        if (!dataBase.exists()) {
//            Log.i(TAG, "db not exist, copy db!");
//            try {
//                copyDatabase(Constants.DATABASE_NAME);
//            } catch (IOException ie) {
//                ie.printStackTrace();
//            }
//        } else {
//            Log.e(TAG, "db already exist!");
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        changeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu");
//        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected");
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search) {
            Toast.makeText(MainActivity.this, "Search is coming soon", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.home) {
            Toast.makeText(MainActivity.this, "Home is pressed", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDrawerFragment.getDrawerToggle().setDrawerIndicatorEnabled(true);
        getSupportActionBar().setTitle(mCurrentCardData);

//        invalidateOptionsMenu();
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerFragment = (FragmentNaviDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navi_drawer);
        mDrawerFragment.setUp(R.id.fragment_navi_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mDrawerFragment.setDrawerListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
//        changeView();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        Toast.makeText(this, "Click on item: " + position, Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (position == 2) {
            FragmentCardView fragCard = new FragmentCardView();
            Bundle bundle = new Bundle();
            bundle.putCharSequence(Constants.CARD_DATA_TYPE, Constants.CARD_TYPE_JLPT_N3);
            fragCard.setArguments(bundle);
            fragCard.setListener(this);
            fragmentTransaction.replace(R.id.container_body, fragCard);
            fragmentTransaction.commit();
        } else if(position == 1) {
            FragmentCardView fragCard = new FragmentCardView();
            Bundle bundle = new Bundle();
            bundle.putCharSequence(Constants.CARD_DATA_TYPE, Constants.CARD_TYPE_JLPT_N2);
            fragCard.setArguments(bundle);
            fragCard.setListener(this);
            fragmentTransaction.replace(R.id.container_body, fragCard);
            fragmentTransaction.commit();
        }  else {
            BlankFragment blankFragment = new BlankFragment();
            fragmentTransaction.replace(R.id.container_body, blankFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onFragmentItemSelected(View view, int position) {
        Toast.makeText(this, "Click on item: " + position, Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentCardViewDetails cardViewDetails = new FragmentCardViewDetails();
        fragmentTransaction.replace(R.id.container_body, cardViewDetails);
        fragmentTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getExtras() == null) {
            Log.d(TAG, "onNewIntent: intent extra is null");
            setIntent(readHistory());
        } else {
            Log.d(TAG, "onNewIntent: " + intent.getExtras().toString());
            setIntent(intent);
        }
        changeView();
    }

    //TODO where did we stop last time? (shared pref)
    private Intent readHistory() {
        Intent intent = getIntent();
        mCurrentCardData = Constants.CARD_TYPE_JLPT_N3;
        mCurrentDisplay = Constants.CARD_VIEW_DISPLAY;
        intent.putExtra(Constants.DISPLAY_TYPE, mCurrentDisplay);
        intent.putExtra(Constants.CARD_DATA_TYPE, mCurrentCardData);
        intent.putExtra(Constants.CARD_ID, 0);
        return intent;
    }

    private void changeView() {
        Intent intent = getIntent();
        mCurrentCardData = intent.getExtras().getString(Constants.CARD_DATA_TYPE);
        mCurrentDisplay = intent.getExtras().getString(Constants.DISPLAY_TYPE);
        mCardID = intent.getExtras().getInt(Constants.CARD_ID);
        Log.d(TAG, "changeView: " + "display: " + mCurrentDisplay + ", data: " + mCurrentCardData + ", id: " + mCardID);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (mCurrentDisplay.equals(Constants.VIEW_PAGER_DISPLAY)) {
            mDrawerFragment.getDrawerToggle().setDrawerIndicatorEnabled(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//            FragmentCardViewDetails detailFrag = new FragmentCardViewDetails();
//            Bundle bundle = new Bundle();
//            bundle.putCharSequence(Constants.CARD_DATA_TYPE, mCurrentCardData);
//            bundle.putInt(Constants.CARD_ID, mCardID);
//            detailFrag.setArguments(bundle);
//            fragmentTransaction.replace(R.id.container_body, detailFrag, "detail_frag");
//            fragmentTransaction.addToBackStack("detail_frag");
//            fragmentTransaction.commit();

            FragmentViewPager fragmentViewPager = new FragmentViewPager();
            Bundle bundle = new Bundle();
            bundle.putCharSequence(Constants.CARD_DATA_TYPE, mCurrentCardData);
            bundle.putInt(Constants.VIEW_PAGER_ID, mCardID);
            fragmentViewPager.setArguments(bundle);
            fragmentTransaction.replace(R.id.container_body, fragmentViewPager, "view_pager_frag");
            fragmentTransaction.addToBackStack("view_pager_frag");
            fragmentTransaction.commit();

        } else if (mCurrentDisplay.equals(Constants.CARD_VIEW_DISPLAY)) {
            mDrawerFragment.getDrawerToggle().setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            FragmentCardView fragCard = new FragmentCardView();
            Bundle bundle = new Bundle();
            bundle.putCharSequence(Constants.CARD_DATA_TYPE, Constants.CARD_TYPE_JLPT_N3);
            fragCard.setArguments(bundle);
            fragCard.setListener(this);
            fragmentTransaction.replace(R.id.container_body, fragCard, "cardview_frag");
            fragmentTransaction.addToBackStack("cardview_frag");
            fragmentTransaction.commit();
        }

    }
}
