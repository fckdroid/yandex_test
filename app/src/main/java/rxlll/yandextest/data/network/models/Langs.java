package rxlll.yandextest.data.network.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by Maksim Sukhotski on 4/15/2017.
 */

public class Langs {

    private String dirs[];
    private LinkedTreeMap<String, String> langs;

    public String[] getDirs() {
        return dirs;
    }

    public LinkedTreeMap<String, String> getLangs() {
        return langs;
    }
}
