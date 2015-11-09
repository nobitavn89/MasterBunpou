package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/11/09.
 */
public class CardDataJLPTN2 extends AbstractCardData{
    @Override
    public ArrayList<CardViewItem> requestData(Context context) {
        ArrayList<CardViewItem> retList = new ArrayList<>();
        SQLiteOpenHelper dbHelper = new MasterBunpouDbHelper(context);
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sortOrder = BunpouEntry._ID + " ASC";
            StringBuilder selectionBuilder = new StringBuilder(BunpouEntry.COLUMN_BUNPOU_ENTRY_TYPE).append(" is ").append("\"").append(Constants.CARD_TYPE_JLPT_N2).append("\"");
            String selection = selectionBuilder.toString();
            Cursor cursor = db.query(BunpouEntry.TABLE_NAME, null, selection, null, null, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String cardType = Constants.CARD_TYPE_JLPT_N2;
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_TITLE));
                    String meaning = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_MEANING));
                    String usage = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_USAGE));
                    String example = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_EXAMPLE));
                    CardViewItem item = new CardViewItem(cardType,title, meaning, usage, example);
                    retList.add(item);
                } while (cursor.moveToNext());
            }
            if(cursor != null) {
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }
}
