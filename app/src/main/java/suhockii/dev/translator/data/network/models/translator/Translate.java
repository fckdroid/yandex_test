package suhockii.dev.translator.data.network.models.translator;

/**
 * Created by Maksim Sukhotski on 4/15/2017.
 */

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

    public String getText() {
        String s = "";
        if (text.length > 0) {
            for (int i = 0; i < text.length; i++) {
                if (i + 1 == text.length) {
                    s = s + text[i];
                } else {
                    s = s + text[i] + ", ";
                }
            }
        }
        return s;
    }
}
