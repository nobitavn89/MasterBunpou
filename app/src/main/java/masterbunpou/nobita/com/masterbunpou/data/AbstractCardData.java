package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;

import java.util.ArrayList;

import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;

/**
 * Created by nobitavn89 on 15/10/22.
 * abstact class for CardData
 */
public abstract class AbstractCardData {
    public abstract ArrayList<CardViewItem> requestData(Context context);
}
