package masterbunpou.nobita.com.masterbunpou.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nobitavn89 on 15/10/22.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector mDetector;
    private ClickListener mListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.mListener = clickListener;
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(child != null && mListener != null) {
                    mListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                }
                super.onLongPress(e);
            }
        });
    }
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if(child != null && mListener!=null && mDetector.onTouchEvent(e)) {
            mListener.onClick(child,rv.getChildLayoutPosition(child));
        }
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }


}