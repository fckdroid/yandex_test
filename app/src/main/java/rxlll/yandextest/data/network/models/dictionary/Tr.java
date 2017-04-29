package rxlll.yandextest.data.network.models.dictionary;

import java.util.List;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class Tr {
    private String text;
    private String pos;
    private String gen;
    private List<Syn> syn;
    private List<Mean> mean;
    private List<Ex> ex;
    private String asp;
    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public String getGen() {
        return gen;
    }

    public List<Syn> getSyn() {
        return syn;
    }

    public List<Mean> getMean() {
        return mean;
    }

    public List<Ex> getEx() {
        return ex;
    }

    public String getAsp() {
        return asp;
    }
}
