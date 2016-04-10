package masterbunpou.nobita.com.masterbunpou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.adapter.NavigationDrawerAdapter;
import masterbunpou.nobita.com.masterbunpou.listener.RecyclerTouchListener;
import masterbunpou.nobita.com.masterbunpou.model.NaviDrawerItem;

/**
 * Create by nobita
 * Fragment used with NavigationDrawerAdapter
 */
public class FragmentNaviDrawer extends Fragment {

    private static String TAG = FragmentNaviDrawer.class.getSimpleName();

    private RecyclerView mRecycleView;
    private ActionBarDrawerToggle mDrawerToggle; //listener for DrawerLayout
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter mAdapter;
    private View mContainView;
    private FragmentDrawerListener mDrawerListener;
//    private BookmarkClickListener mBookmarkClickListener;

    private static String[] mTitles = null;


    public FragmentNaviDrawer() {
        // Required empty public constructor
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        mDrawerListener = listener;
    }

    /*
    just for test. Later, this function will be replace by loading data from db or....
     */
    public static List<NaviDrawerItem> getData() {
        Log.d(TAG, "getDrawerData, mTitles[0]: " + mTitles[0]);
        List<NaviDrawerItem> data = new ArrayList<>();

        //preparing...
        for (int i = 0; i < mTitles.length; i++) {
            NaviDrawerItem item = new NaviDrawerItem();
            item.setTitle(mTitles[i]);
            data.add(item);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //drawer labels
        mTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.frag_navi_drawer, container, false);
        mRecycleView = (RecyclerView) layout.findViewById(R.id.frag_jlpt_item_container);

        mAdapter = new NavigationDrawerAdapter(getActivity(), getData());
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecycleView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDrawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(mContainView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //quite stupid but we need to fix the position
        TextView txtBookMark = (TextView) layout.findViewById(R.id.txt_title_bookmark);
        txtBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "This feature is coming soon", Toast.LENGTH_SHORT).show();
                mDrawerListener.onDrawerItemSelected(null, 5);
                mDrawerLayout.closeDrawer(mContainView);
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        Log.d(TAG, "setUp, fragmentID: " + fragmentId);
        mContainView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                Log.d(TAG, "onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

//    public interface BookmarkClickListener {
//        void onClickListener();
//    }

}
