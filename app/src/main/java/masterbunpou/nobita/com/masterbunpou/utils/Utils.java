package masterbunpou.nobita.com.masterbunpou.utils;

import android.content.Context;
import android.util.Log;

import java.util.List;

import masterbunpou.nobita.com.masterbunpou.data.DataManager;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;

/**
 * Created by nobitavn89 on 15/10/24.
 */
public class Utils {

    public static List<CardViewItem> getData(Context context, String mCardDataType) {
        List<CardViewItem> list = DataManager.getInstance().getData(context, mCardDataType);
        Log.d("getData", "getData, size: " + list.size());
        return list;
    }
}
