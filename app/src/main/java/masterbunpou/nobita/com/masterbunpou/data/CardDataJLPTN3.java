package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/10/22.
 */
public class CardDataJLPTN3 extends AbstractCardData {

    @Override
    public ArrayList<CardViewItem> requestData(Context context) {
        ArrayList<CardViewItem> retList = new ArrayList<>();
        SQLiteOpenHelper dbHelper = new MasterBunpouDbHelper(context);
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //TODO later, move this to SQLiteOpenHelper
//            String[] projection = {
//
//            };
            String sortOrder = BunpouEntry._ID + " ASC";
//            String selection = SELECT * FROM master_bunpou WHERE entry_type is "N2"
            StringBuilder selectionBuilder = new StringBuilder(BunpouEntry.COLUMN_BUNPOU_ENTRY_TYPE).append(" is ").append("\"").append(Constants.CARD_TYPE_JLPT_N3).append("\"");
            String selection = selectionBuilder.toString();
            Cursor cursor = db.query(BunpouEntry.TABLE_NAME, null, selection, null, null, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();
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
