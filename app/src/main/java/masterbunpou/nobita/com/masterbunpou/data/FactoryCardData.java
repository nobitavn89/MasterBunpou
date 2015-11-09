package masterbunpou.nobita.com.masterbunpou.data;

import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/10/22.
 * singleton Factory to create cardData
 */
public class FactoryCardData {
    private static FactoryCardData instance;

    public static synchronized FactoryCardData getInstance() {
        if(instance == null) {
            instance = new FactoryCardData();
        }
        return instance;
    }

    public AbstractCardData createCardData(String type) {
       if(type == Constants.CARD_TYPE_JLPT_N3) {
            return new CardDataJLPTN3();
       } else if (type == Constants.CARD_TYPE_JLPT_N2) {
           return new CardDataJLPTN2();
       }
        return null;
    }
}
