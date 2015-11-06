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
        //TODO need to filter more
        ArrayList<CardViewItem> retList = new ArrayList<>();
        SQLiteOpenHelper dbHelper = new MasterBunpouDbHelper(context);
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] projection = {

            };
            String sortOrder = BunpouEntry._ID + " DESC";
            Cursor cursor = db.query(BunpouEntry.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String cardType = Constants.CARD_TYPE_JLPT_N3;
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_TITLE));
                    String meaning = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_MEANING));
                    String usage = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_USAGE));
                    String example = cursor.getString(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ENTRY_EXAMPLE));
                    CardViewItem item = new CardViewItem(cardType,title, meaning, usage, example);
                    retList.add(item);
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }
}
