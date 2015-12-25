package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/12/09.
 */
public class CardDataBookmark extends AbstractCardData{

    @Override
    public ArrayList<CardViewItem> requestData(Context context) {
        SQLiteOpenHelper sqLiteOpenHelper = new MasterBunpouDbHelper(context);
        ArrayList<CardViewItem> retList = new ArrayList<>();
        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            StringBuilder selectionBuilder = new StringBuilder(BunpouEntry.COLUMN_BUNPOU_ENTRY_BOOKMARK).append(" is ").append(1);
            String selection = selectionBuilder.toString();
            String order = BunpouEntry._ID + " ASC";
            Cursor cursor = db.query(BunpouEntry.TABLE_NAME, null, selection, null, null, null, order);
            if(cursor != null) {
                cursor.moveToFirst();
                do {
                    String cardType = Constants.CARD_TYPE_BOOKMARKS;
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
            } else {
                db.close();
                return null;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retList;
    }
}
