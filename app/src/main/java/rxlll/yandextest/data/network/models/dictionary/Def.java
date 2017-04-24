package rxlll.yandextest.data.network.models.dictionary;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class Def {
    private String text;
    private String pos;
    private String ts;
    private Tr[] tr;

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public String getTs() {
        return ts;
    }

    public Tr[] getTr() {
        return tr;
    }
}
