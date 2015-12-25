package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import masterbunpou.nobita.com.masterbunpou.listener.DataReceiverListener;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;

/**
 * Created by nobitavn89 on 15/10/31.
 */
public class LoadDataAsyncTask extends AsyncTask {
    private Context mContext;
    private String mType;
    private DataReceiverListener mListener;
    private String mQuery;

    public LoadDataAsyncTask (Context context, String type, DataReceiverListener listener) {
        mContext = context;
        mType = type;
        mListener = listener;
        mQuery = null;
    }

    public LoadDataAsyncTask (Context context, String type, DataReceiverListener listener, String query) {
        mContext = context;
        mType = type;
        mListener = listener;
        mQuery = query;
    }

    @Override
    protected ArrayList<CardViewItem> doInBackground(Object[] params) {
        //slightly special proceed for search. maybe this is not a good solution
        // =.=
        AbstractCardData data = FactoryCardData.getInstance().createCardData(mType);
        return data.requestData(mContext);
    }

    @Override
    protected void onPostExecute(Object list) {
        DataManager.getInstance().mListData.put(mType, (ArrayList<CardViewItem>) list);
        mListener.onDataReceive((ArrayList<CardViewItem>) list);
//        super.onPostExecute(o);
    }
}
