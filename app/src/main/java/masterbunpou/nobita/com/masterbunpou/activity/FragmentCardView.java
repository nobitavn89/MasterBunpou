package masterbunpou.nobita.com.masterbunpou.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.adapter.CardViewAdapter;
import masterbunpou.nobita.com.masterbunpou.data.DataManager;
import masterbunpou.nobita.com.masterbunpou.listener.DataReceiverListener;
import masterbunpou.nobita.com.masterbunpou.listener.FragmentItemClickListener;
import masterbunpou.nobita.com.masterbunpou.listener.RecyclerTouchListener;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCardView extends Fragment {
    private static final String TAG = FragmentCardView.class.getSimpleName();
    private String mType; //type of fragment
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private CardViewAdapter mAdapter;
    FragmentItemClickListener mListener;

//    private List<CardViewItem> mListCardViewItem = Collections.emptyList();


    public FragmentCardView() {
        // Required empty public constructor
    }


    public void setListener(FragmentItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mType = (String)getArguments().get(Constants.CARD_DATA_TYPE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mType);
        View layout = inflater.inflate(R.layout.frag_card_view, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycle_view_card);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mProgressBar = (ProgressBar) layout.findViewById(R.id.progress_bar_main_view);
        mProgressBar.setVisibility(View.VISIBLE);
        //setup data
//        getData();

//        final ProgressDialog loadData = ProgressDialog.show(getActivity(), getActivity().getString(R.string.data_loading_noti), getActivity().getString(R.string.please_wait));
//        mProgressBar.setVisibility();
        DataManager.getInstance().requestData(getActivity(), mType, new DataReceiverListener() {
            @Override
            public void onDataReceive(List<CardViewItem> list) {
//                if(loadData.isShowing()) {
//                    loadData.dismiss();
//                }
                mProgressBar.setVisibility(View.GONE);
                if(list.isEmpty()) {
                    Toast.makeText(getActivity(),getActivity().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                }
                mAdapter = new CardViewAdapter(list);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
//        mAdapter = new CardViewAdapter(getData());
//        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Log.d(TAG, "item click: " + position);
//                mListener.onFragmentItemSelected(view, position);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    //TODO getdata - can change to Utils.getData(type)
    public List<CardViewItem> getData() {
        //fake data to test
        List<CardViewItem> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            CardViewItem item = new CardViewItem("~ことになっている", "Nói về 1 việc đã được cơ quan, tổ chức hoặc người khác quyết định cho mình.");
//            list.add(item);
//        }
        list = DataManager.getInstance().getData(getActivity(), mType);
        Log.d(TAG, "getData, size: " + list.size());
        return list;
    }

}
