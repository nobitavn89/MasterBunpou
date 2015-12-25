package masterbunpou.nobita.com.masterbunpou.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.adapter.SearchViewAdapter;
import masterbunpou.nobita.com.masterbunpou.data.BunpouEntry;
import masterbunpou.nobita.com.masterbunpou.listener.SearchDataListener;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;
import masterbunpou.nobita.com.masterbunpou.utils.Utils;

public class BunpouSearchActivity extends AppCompatActivity {
    private static final String TAG = BunpouSearchActivity.class.getSimpleName();
    private Cursor mCursor;
    private ListView mListView;
    private TextView mEmptText;
    private ProgressBar mProgressBar;
    private ArrayList<CardViewItem> mListData = new ArrayList<>();
    private String mQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunpou_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        mListView = (ListView) findViewById(R.id.list_search_view);
        mEmptText = (TextView) findViewById(R.id.empty_search_view);
        mProgressBar = (ProgressBar) findViewById(R.id.search_progress_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView.setVisibility(View.INVISIBLE);
        mEmptText.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        handleIntent(getIntent());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "item click: " + position);
                CardViewItem itemSelected = mListData.get(position);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                FragmentSearchViewDetails detailSearch = new FragmentSearchViewDetails();
                //data to send to fragment
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.CARD_VIEW_DETAIL_ID, itemSelected.getCardId());
                bundle.putString(Constants.CARD_VIEW_DETAIL_TITLE, itemSelected.getTitle());
                bundle.putString(Constants.CARD_VIEW_DETAIL_MEANING, itemSelected.getMeaning());
                bundle.putString(Constants.CARD_VIEW_DETAIL_USAGE, itemSelected.getUsage());
                bundle.putString(Constants.CARD_VIEW_DETAIL_EXAMPLE, itemSelected.getEx());
                bundle.putInt(Constants.CARD_VIEW_DETAIL_BOOKMARK_STATE, itemSelected.getBookmarkState());
                detailSearch.setArguments(bundle);
                fragmentTransaction.add(R.id.search_container_body, detailSearch);
                fragmentTransaction.addToBackStack("main_frag");
                fragmentTransaction.commit();


            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionItemSelected");
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setTitle(mQuery);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            mQuery = query;
            Log.d("nobita", "query string: " + query);
            //should run on background
            getSupportActionBar().setTitle(query);
            mCursor = Utils.doSearchonAsync(getApplicationContext(), query, new SearchDataListener() {
                @Override
                public void onSearchFinish(Cursor cursor) {
                    mProgressBar.setVisibility(View.GONE);
                    mCursor = cursor;
                    if(null == mCursor || (mCursor.getCount() == 0)) {
                        mEmptText.setVisibility(View.VISIBLE);
                    } else {
                        Log.d(TAG, "we have search data: " + mCursor.getCount());
                        mListView.setVisibility(View.VISIBLE);
                        do {

                            String cardType = Constants.CARD_TYPE_JLPT_N3;
                            int id = cursor.getInt(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ID));
                            int id_entry = cursor.getInt(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_ID));
                            String title = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_TITLE));
                            String meaning = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_MEANING));
                            String usage = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_USAGE));
                            String example = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_EXAMPLE));
                            int isBookmark = cursor.getInt(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_BOOKMARK));
                            CardViewItem item = new CardViewItem(id, id_entry, cardType,title, meaning, usage, example, isBookmark);
                            mListData.add(item);
                        } while (cursor.moveToNext());
                        mCursor.close();
                        //set view
                        SearchViewAdapter searchViewAdapter = new SearchViewAdapter(getApplicationContext(),mListData);
                        mListView.setAdapter(searchViewAdapter);
                    }

                }
            });
        }
    }
}
