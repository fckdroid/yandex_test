package suhockii.dev.translator.data.network.models.dictionary;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class Syn {
    private String text;
    private String pos;
    private String gen;
    private String asp;

    public Syn(String text, String pos, String gen, String asp) {
        this.text = text;
        this.pos = pos;
        this.gen = gen;
        this.asp = asp;
    }

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public String getGen() {
        return gen;
    }

    public String getAsp() {
        return asp;
    }
}
