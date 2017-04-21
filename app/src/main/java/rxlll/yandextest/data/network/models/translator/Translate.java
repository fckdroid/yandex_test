package rxlll.yandextest.data.network.models.translator;

/** Created by Maksim Sukhotski on 4/15/2017. */

public class Translate {
    private Detect detected;
    private String lang;
    private String[] text;

    public Detect getDetected() {
        return detected;
    }
    public String getLang() {
        return lang;
    }
    public String[] getText() {
        return text;
    }
}
