package rxlll.yandextest.data.repositories.database;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import rxlll.yandextest.data.network.models.dictionary.Dictionary;
import rxlll.yandextest.data.network.models.translator.Translate;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

@Entity(nameInDb = "translations")
public final class Translation {

    @Id
    private Long id;

    @NonNull
    private String dictionary;

    @NonNull
    private String translate;

    @NonNull
    private String dir;

    @NotNull
    private String original;

    @NonNull
    private boolean isFavorite;

    @Transient
    private Translate translatePretty;

    @Transient
    private Dictionary dictionaryPretty;

    @Generated(hash = 1801338536)
    public Translation(Long id, @NonNull String dictionary,
                       @NonNull String translate, @NonNull String dir,
                       @NonNull String original, boolean isFavorite) {
        this.id = id;
        this.dictionary = dictionary;
        this.translate = translate;
        this.dir = dir;
        this.original = original;
        this.isFavorite = isFavorite;
    }

    @Generated(hash = 321689573)
    public Translation() {
    }

    public Dictionary getDictionaryPretty() {
        if (dictionaryPretty == null)
            dictionaryPretty = new Gson().fromJson(dictionary, Dictionary.class);
        return dictionaryPretty;
    }

    public Translate getTranslatePretty() {
        if (translatePretty == null)
            translatePretty = new Gson().fromJson(translate, Translate.class);
        return translatePretty;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictionary() {
        return this.dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    public String getTranslate() {
        return this.translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getDir() {
        return this.dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isNotEmpty() {
        return id != null;
    }
}
