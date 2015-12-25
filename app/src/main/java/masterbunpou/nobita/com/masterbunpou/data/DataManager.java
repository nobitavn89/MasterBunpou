package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import masterbunpou.nobita.com.masterbunpou.listener.DataReceiverListener;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;

/**
 * Created by nobitavn89 on 15/10/23.
 */
public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();
    private static DataManager mInstance;
    public static HashMap<String, ArrayList<CardViewItem>> mListData;

    public static synchronized DataManager getInstance() {
        if(mInstance == null) {
            mInstance = new DataManager();
            mListData = new HashMap<>();
        }
        return mInstance;
    }

    public ArrayList<CardViewItem> getData(Context context, String type) {
        if(mListData.get(type) == null) {
            Log.i(TAG, "list empty, try to open db and getdata");
            AbstractCardData data = FactoryCardData.getInstance().createCardData(type);
            mListData.put(type, data.requestData(context));
        }
        return mListData.get(type);
    }

    public void requestData(Context context, String type, DataReceiverListener listener) {
        if(mListData.get(type) != null) {
            listener.onDataReceive(mListData.get(type));
        } else {
            LoadDataAsyncTask task = new LoadDataAsyncTask(context,type,listener);
            task.execute();
        }
    }

//    public void doSearch(Context context, )
}
