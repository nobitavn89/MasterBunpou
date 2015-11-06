package masterbunpou.nobita.com.masterbunpou.listener;

import java.util.List;

import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;

/**
 * Created by nobitavn89 on 15/10/31.
 */
public interface DataReceiverListener {
    void onDataReceive(List<CardViewItem> list);
}
