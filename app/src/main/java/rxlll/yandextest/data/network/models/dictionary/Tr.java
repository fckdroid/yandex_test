package rxlll.yandextest.data.network.models.dictionary;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

class Tr {
    private String text;
    private String pos;
    private String gen;
    private Syn[] syn;
    private Mean[] mean;

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public String getGen() {
        return gen;
    }

    public Syn[] getSyn() {
        return syn;
    }

    public Mean[] getMean() {
        return mean;
    }
}
