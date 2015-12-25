package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import masterbunpou.nobita.com.masterbunpou.listener.SearchDataListener;

/**
 * Created by nobitavn89 on 15/12/25.
 */
public class SearchDataAsync extends AsyncTask<String,Void,Void> {
    private SearchDataListener mListener;
    private Context mContext;
    private Cursor mCursor;
    public SearchDataAsync(Context context,SearchDataListener listener) {
        mContext = context;
        mListener = listener;
        mCursor = null;
    }
    @Override
    protected Void doInBackground(String... params) {
        String query = params[0];
        try {
            SQLiteOpenHelper sqLiteOpenHelper = new MasterBunpouDbHelper(mContext);
            SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
            String sortOrder = BunpouEntry.COLUMN_BUNPOU_ID + " ASC";
//            String[] columns = {BunpouEntry.COLUMN_BUNPOU_ID, BunpouEntry.COLUMN_BUNPOU_ENTRY_TITLE, BunpouEntry.COLUMN_BUNPOU_ENTRY_MEANING};
            //SELECT * FROM master_bunpou WHERE entry_title LIKE '%query%'
            StringBuilder sqlBuider = new StringBuilder(BunpouEntry.COLUMN_BUNPOU_ENTRY_TITLE).append(" LIKE ").append("\'").append("%").append(query).append("%").append("\'");
            String selection = sqlBuider.toString();
            Log.d("nobita", "selection: " + selection);
            mCursor = database.query(BunpouEntry.TABLE_NAME, null, selection, null, null, null, sortOrder);
            Log.d("nobita", "cursor: " + mCursor);
            if(null != mCursor) {
                mCursor.moveToFirst();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.onSearchFinish(mCursor);
    }
}
