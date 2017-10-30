package rxlll.yandextest.data.repositories.database;

import android.util.Pair;

import com.google.gson.Gson;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
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
    private Long originalId;
    private Long translationId;

    @ToOne(joinProperty = "originalId")
    private Lang originalLang;

    @ToOne(joinProperty = "translationId")
    private Lang translationLang;

    @NotNull
    private String original;

    @NotNull
    private String direction;

    private String dictionaryJson;

    private String translateJson;

    private boolean isFavorite;

    @Transient
    private Translate translateObject;

    @Transient
    private Dictionary dictionaryObject;

    @Transient
    private Pair<Lang, Lang> dir;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 664316826)
    private transient TranslationDao myDao;
    @Generated(hash = 1468236251)
    private transient Long originalLang__resolvedKey;
    @Generated(hash = 1168386681)
    private transient Long translationLang__resolvedKey;

    @Generated(hash = 510044009)
    public Translation(Long id, Long originalId, Long translationId, @NotNull String original,
                       @NotNull String direction, String dictionaryJson, String translateJson,
                       boolean isFavorite) {
        this.id = id;
        this.originalId = originalId;
        this.translationId = translationId;
        this.original = original;
        this.direction = direction;
        this.dictionaryJson = dictionaryJson;
        this.translateJson = translateJson;
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

    public Long getOriginalId() {
        return this.originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

    public Long getTranslationId() {
        return this.translationId;
    }

    public void setTranslationId(Long translationId) {
        this.translationId = translationId;
    }

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDictionaryJson() {
        return this.dictionaryJson;
    }

    public void setDictionaryJson(String dictionaryJson) {
        this.dictionaryJson = dictionaryJson;
    }

    public String getTranslateJson() {
        return this.translateJson;
    }

    public void setTranslateJson(String translateJson) {
        this.translateJson = translateJson;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Dictionary getDictionaryObject() {
        if (dictionaryObject == null)
            dictionaryObject = new Gson().fromJson(dictionaryJson, Dictionary.class);
        return dictionaryObject;
    }

    public Translate getTranslateObject() {
        if (translateObject == null)
            translateObject = new Gson().fromJson(translateJson, Translate.class);
        return translateObject;
    }

    public Pair<Lang, Lang> getDir() {
        if (dir == null) dir = new Pair<>(getOriginalLang(), getTranslationLang());
        return dir;
    }

    public void setDir(Pair<Lang, Lang> dir) {
        setOriginalLang(dir.first);
        setTranslationLang(dir.second);
    }

    public boolean isNotEmpty() {
        return id != null;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1696348695)
    public Lang getOriginalLang() {
        Long __key = this.originalId;
        if (originalLang__resolvedKey == null || !originalLang__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LangDao targetDao = daoSession.getLangDao();
            Lang originalLangNew = targetDao.load(__key);
            synchronized (this) {
                originalLang = originalLangNew;
                originalLang__resolvedKey = __key;
            }
        }
        return originalLang;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1634870968)
    public void setOriginalLang(Lang originalLang) {
        synchronized (this) {
            this.originalLang = originalLang;
            originalId = originalLang == null ? null : originalLang.getId();
            originalLang__resolvedKey = originalId;
        }
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 135966611)
    public Lang getTranslationLang() {
        Long __key = this.translationId;
        if (translationLang__resolvedKey == null
                || !translationLang__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LangDao targetDao = daoSession.getLangDao();
            Lang translationLangNew = targetDao.load(__key);
            synchronized (this) {
                translationLang = translationLangNew;
                translationLang__resolvedKey = __key;
            }
        }
        return translationLang;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 619377946)
    public void setTranslationLang(Lang translationLang) {
        synchronized (this) {
            this.translationLang = translationLang;
            translationId = translationLang == null ? null : translationLang.getId();
            translationLang__resolvedKey = translationId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 618685332)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTranslationDao() : null;
    }
}
