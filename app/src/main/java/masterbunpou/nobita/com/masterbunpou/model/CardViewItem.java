package masterbunpou.nobita.com.masterbunpou.model;

/**
 * Created by nobitavn89 on 15/10/19.
 * Class saves CardView data
 */
public class CardViewItem {

    private String mCardType;
    private String mTitle;
    private String mMeaning;
    private String mUsage;
    private String mExample;

    public CardViewItem() {

    }

    public CardViewItem(String cardType, String title/*, String meaning*/, String meaning) {
        mCardType = cardType;
        mTitle = title;
//        mMeaning = meaning;
        mMeaning = meaning;
    }

    public CardViewItem(String cardType, String title, String meaning, String usage, String ex) {
        mCardType = cardType;
        mTitle = title;
        mMeaning = meaning;
        mUsage = usage;
        mExample = ex;
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
}
