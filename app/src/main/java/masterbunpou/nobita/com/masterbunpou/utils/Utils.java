package masterbunpou.nobita.com.masterbunpou.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import masterbunpou.nobita.com.masterbunpou.data.BunpouEntry;
import masterbunpou.nobita.com.masterbunpou.data.DataManager;
import masterbunpou.nobita.com.masterbunpou.data.MasterBunpouDbHelper;
import masterbunpou.nobita.com.masterbunpou.data.SearchDataAsync;
import masterbunpou.nobita.com.masterbunpou.listener.SearchDataListener;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;

/**
 * Created by nobitavn89 on 15/10/24.
 */
public class Utils {
    private static final String TAG = "Utils";
    public static List<CardViewItem> getData(Context context, String mCardDataType) {
        List<CardViewItem> list = DataManager.getInstance().getData(context, mCardDataType);
        Log.d("getData", "getData, size: " + list.size());
        return list;
    }

    public static boolean bookmarkCard(Context context, int entry_id, String cardType, int bookmark) {
        SQLiteOpenHelper sqLiteOpenHelper = new MasterBunpouDbHelper(context);
        try{
            SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
            //find item to bookmark
            StringBuilder selectionBuilder = new StringBuilder(BunpouEntry.COLUMN_BUNPOU_ENTRY_ID).append(" is ").append(entry_id).append(" AND ").append(BunpouEntry.COLUMN_BUNPOU_ENTRY_TYPE).append(" is ").append("\"").append(cardType).append("\"");
            String selection = selectionBuilder.toString();
            Cursor cursor = db.query(BunpouEntry.TABLE_NAME,null,selection,null,null, null,null);
            if(cursor == null) {
                Toast.makeText(context, "Error occur when bookmark", Toast.LENGTH_SHORT).show();
                db.close();
                return false;
            } else {
                cursor.moveToFirst();
                ContentValues values = new ContentValues();
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BunpouEntry.COLUMN_BUNPOU_ID));
                Log.d("nobita", "found id: " + id);
                values.put(BunpouEntry.COLUMN_BUNPOU_ENTRY_BOOKMARK, bookmark);
                db.update(BunpouEntry.TABLE_NAME, values, BunpouEntry.COLUMN_BUNPOU_ID + " = ?", new String[]{Integer.toString(id)});
                cursor.close();
                db.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean bookmarkCard(Context context, int id, int bookmark) {
        SQLiteOpenHelper sqLiteOpenHelper = new MasterBunpouDbHelper(context);
        try {
            SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BunpouEntry.COLUMN_BUNPOU_ENTRY_BOOKMARK, bookmark);
            db.update(BunpouEntry.TABLE_NAME, values, BunpouEntry.COLUMN_BUNPOU_ID + " = ?", new String[]{Integer.toString(id)});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Cursor doSearch(Context context, String query) {
        Cursor cursor = null;
        try {
            SQLiteOpenHelper sqLiteOpenHelper = new MasterBunpouDbHelper(context);
            SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
            String sortOrder = BunpouEntry.COLUMN_BUNPOU_ID + " ASC";
//            String[] columns = {BunpouEntry.COLUMN_BUNPOU_ID, BunpouEntry.COLUMN_BUNPOU_ENTRY_TITLE, BunpouEntry.COLUMN_BUNPOU_ENTRY_MEANING};
            //SELECT * FROM master_bunpou WHERE entry_title LIKE '%query%'
            StringBuilder sqlBuider = new StringBuilder(BunpouEntry.COLUMN_BUNPOU_ENTRY_TYPE).append(" LIKE ").append("\'").append(query).append("\'");
            String selection = sqlBuider.toString();
            cursor = database.query(BunpouEntry.TABLE_NAME, null, selection, null, null, null, sortOrder);
            Log.d(TAG, "cursor: " + cursor);
            if(null != cursor) {
                cursor.moveToFirst();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }


    public static Cursor doSearchonAsync(final Context context, String query, SearchDataListener listener) {
        new SearchDataAsync(context,listener).execute(new String[]{query});
        return null;
    }

}
