package masterbunpou.nobita.com.masterbunpou.model;

/**
 * Created by nobitavn89 on 15/10/19.
 * Class saves CardView data
 */
public class CardViewItem {

    private int mCardId;
    private int mCardEntryId;
    private String mCardType;
    private String mTitle;
    private String mMeaning;
    private String mUsage;
    private String mExample;
    private int mIsBookmark;

    public CardViewItem() {

    }

    public CardViewItem(String cardType, String title/*, String meaning*/, String meaning) {
        mCardType = cardType;
        mTitle = title;
//        mMeaning = meaning;
        mMeaning = meaning;
    }

    public CardViewItem(String cardType, String title, String meaning, String usage, String ex, int isBookmark) {
        mCardType = cardType;
        mTitle = title;
        mMeaning = meaning;
        mUsage = usage;
        mExample = ex;
        mIsBookmark = isBookmark;
    }

    public CardViewItem(int cardId, int cardEntryId, String cardType, String title, String meaning, String usage, String ex, int isBookmark) {
        mCardId = cardId;
        mCardEntryId = cardEntryId;
        mCardType = cardType;
        mTitle = title;
        mMeaning = meaning;
        mUsage = usage;
        mExample = ex;
        mIsBookmark = isBookmark;
    }


    public int getCardId () {
        return mCardId;
    }
    public int getCardEntryId () {
        return mCardEntryId;
    }
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setCardType (String type) {
        mCardType = type;
    }

    public String getCardDataType () {
        return mCardType;
    }

    public String getUsage() {
        return mUsage;
    }

    public void setUsage(String usage) {
        this.mUsage = usage;
    }

    public String getEx() {
        return mExample;
    }

    public void setEx(String ex) {
        this.mExample = ex;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public void setMeaning(String meaning) {
        mMeaning = meaning;
    }

    public void setBookmarkState (int bookmarkState) {
        mIsBookmark = bookmarkState;
    }

    public int getBookmarkState () {
        return mIsBookmark;
    }

}
