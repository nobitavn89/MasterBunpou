package masterbunpou.nobita.com.masterbunpou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.model.NaviDrawerItem;

/**
 * Created by nobitavn89 on 15/10/16.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NaviViewHolder> {
    List<NaviDrawerItem> mListItem = Collections.emptyList();

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public NavigationDrawerAdapter(Context context, List<NaviDrawerItem> data) {
        mContext = context;
        mListItem = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void delete (int position) {
        mListItem.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public NaviViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.navi_drawer_row, parent, false);
        NaviViewHolder holder = new NaviViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NaviViewHolder holder, int position) {
        NaviDrawerItem currentItem = mListItem.get(position);
        holder.mTitle.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public static class NaviViewHolder extends  RecyclerView.ViewHolder {

        TextView mTitle;
        public NaviViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.nav_item_title);
        }
    }
}
