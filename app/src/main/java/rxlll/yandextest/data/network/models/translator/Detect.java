package rxlll.yandextest.data.network.models.translator;

import rxlll.yandextest.data.repositories.database.Lang;

/** Created by Maksim Sukhotski on 4/15/2017. */

public class Detect {
    private String lang;
    private Lang langPretty;

    public Lang getLangPretty() {
        return langPretty;
    }

    public void setLangPretty(Lang langPretty) {
        this.langPretty = langPretty;
    }

    public String getLang ()
    {
        return lang;
    }
}
