package masterbunpou.nobita.com.masterbunpou.model;

/**
 * Created by nobitavn89 on 15/10/16.
 */
public class NaviDrawerItem {
    private boolean mShowNotify;
    private String mTitle;

    public NaviDrawerItem() {

    }

    public NaviDrawerItem(String title, boolean showNotify) {
        mShowNotify = showNotify;
        mTitle = title;
    }

    public boolean isShowNotify() {
        return mShowNotify;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setShowNotify(boolean mShowNotify) {
        this.mShowNotify = mShowNotify;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
