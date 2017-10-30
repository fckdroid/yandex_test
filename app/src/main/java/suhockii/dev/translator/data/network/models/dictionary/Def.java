package suhockii.dev.translator.data.network.models.dictionary;

import java.util.List;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class Def {
    private String text;
    private String pos;
    private String ts;
    private List<Tr> tr;

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public String getTs() {
        return ts;
    }

    public List<Tr> getTr() {
        return tr;
    }
}
