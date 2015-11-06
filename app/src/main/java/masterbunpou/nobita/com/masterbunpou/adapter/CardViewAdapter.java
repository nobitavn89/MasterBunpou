package masterbunpou.nobita.com.masterbunpou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import masterbunpou.nobita.com.masterbunpou.R;
import masterbunpou.nobita.com.masterbunpou.activity.MainActivity;
import masterbunpou.nobita.com.masterbunpou.model.CardViewItem;
import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/10/19.
 * Adapter for display a list of cardview, such as a list of N3 grammar
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {
    List<CardViewItem> mListCard = Collections.emptyList();

    public CardViewAdapter(List<CardViewItem> listCard) {
        mListCard = listCard;
    }


    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        final CardViewItem item = mListCard.get(position);
        holder.mTitle.setText(item.getTitle());
        holder.mContent.setText(item.getMeaning());
        holder.mLearnMoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("nobita", "learnmore button: " + position + " is clicked");
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                intent.putExtra(Constants.DISPLAY_TYPE, Constants.VIEW_PAGER_DISPLAY);
                intent.putExtra(Constants.CARD_DATA_TYPE, item.getCardDataType());
                intent.putExtra(Constants.CARD_ID, position);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View cardViewItem = LayoutInflater.from(context).inflate(R.layout.cardview_item, parent, false);
        CardView cardView = (CardView) cardViewItem.findViewById(R.id.card_view);
        //backward compatibility (from android developer)
        cardView.setUseCompatPadding(true);
        float maxCardElevation = cardView.getMaxCardElevation();
        cardView.setCardElevation(maxCardElevation > 8 ? 8 : maxCardElevation);
        int sidePadding = (int) (maxCardElevation + (1-Math.cos(Math.PI/4))*context.getResources().getDimension(R.dimen.card_view_radius));
        int topPadding = (int) (maxCardElevation*1.5 + (1-Math.cos(Math.PI/4))*context.getResources().getDimension(R.dimen.card_view_radius));
        cardView.setPadding(sidePadding, topPadding, sidePadding, topPadding);
        CardViewHolder holder = new CardViewHolder(cardViewItem);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mListCard.size();
    }

    //From android developer: this class provides a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mMeaning;
        public TextView mContent;
        public Button mLearnMoreButton;

        public CardViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.txt_card_view_title);
            mMeaning = (TextView) v.findViewById(R.id.txt_card_view_meaning);
            mContent = (TextView) v.findViewById(R.id.txt_card_view_content);
            mLearnMoreButton = (Button) v.findViewById(R.id.btn_learn_more);
        }
    }
}
