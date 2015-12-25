package masterbunpou.nobita.com.masterbunpou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;

/**
 * Created by nobitavn89 on 15/12/25.
 */
public class SearchViewAdapter extends ArrayAdapter<CardViewItem> {
    ArrayList<CardViewItem> mIdMap = new ArrayList<>();
    public SearchViewAdapter (Context context, ArrayList<CardViewItem> list) {
        super (context,0, list);
        mIdMap = list;
    }

    @Override
    public int getCount() {
        return mIdMap.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardViewItem item = getItem(position);
        SearchItemViewHolder viewHolder;
        if( convertView == null) {
            viewHolder = new SearchItemViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_search, parent,false);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.txt_search_item_title);
            viewHolder.mMeaning = (TextView) convertView.findViewById(R.id.txt_search_item_meaning);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SearchItemViewHolder)convertView.getTag();
        }
        viewHolder.mTitle.setText(item.getTitle());
        viewHolder.mMeaning.setText(item.getMeaning());
        return convertView;
    }

    static class SearchItemViewHolder{
        TextView mTitle;
        TextView mMeaning;
    }
}
