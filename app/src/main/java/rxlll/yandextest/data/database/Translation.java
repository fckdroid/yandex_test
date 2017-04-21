package rxlll.yandextest.data.database;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

@Entity(nameInDb = "translations")
public final class Translation {

    @Id
    private Long id;

    @NotNull
    private String text;
    @NotNull
    private String lang;

    @NonNull
    private String translate;

    @NonNull
    private boolean isFavorite;

    @Generated(hash = 1903699825)
    public Translation(Long id, @NotNull String text, @NotNull String lang,
                       @NotNull String translate, boolean isFavorite) {
        this.id = id;
        this.text = text;
        this.lang = lang;
        this.translate = translate;
        this.isFavorite = isFavorite;
    }

    @Generated(hash = 321689573)
    public Translation() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTranslate() {
        return this.translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

}
