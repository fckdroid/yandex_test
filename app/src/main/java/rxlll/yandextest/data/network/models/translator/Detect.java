package rxlll.yandextest.data.network.models.translator;

import rxlll.yandextest.data.repositories.database.Lang;

/** Created by Maksim Sukhotski on 4/15/2017. */

public class Detect {
    private String lang;
    private Lang langObject;

    public Lang getLangObject() {
        return langObject;
    }

    public void setLangObject(Lang langObject) {
        this.langObject = langObject;
    }

    public String getLang ()
    {
        return lang;
    }
}
