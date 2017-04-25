package rxlll.yandextest.data.repositories.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;
import rxlll.yandextest.data.repositories.database.Lang;

import static rxlll.yandextest.App.UI;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public class PreferencesRepositoryImpl implements PreferencesRepository {
    private static final String PREFERENCES_NAME = "app-preferences";
    private static final String KEY_DIRS = "key_dirs";
    private static final String KEY_LANG_LEFT = "key_lang_left";
    private static final String KEY_LANG_RIGHT = "key_lang_right";
    private static final String KEY_AUTO_DETECT_SETTING = "key_auto_detect_setting";

    private SharedPreferences preferences;

    public PreferencesRepositoryImpl(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
    }

    @Override
    public Completable putDirs(Set<String> dirs) {
        return Completable.fromAction(() -> preferences.edit().putStringSet(KEY_DIRS, dirs).commit());
    }

    @Override
    public Single<Set<String>> getDirs() {
        return Single.fromCallable(() -> preferences.getStringSet(KEY_DIRS, new HashSet<>()));
    }

    @Override
    public Completable putDir(Pair<Lang, Lang> dir) {
        return Completable.fromAction(() -> {
            preferences.edit().putString(KEY_LANG_LEFT, new Gson().toJson(dir.first)).apply();
            preferences.edit().putString(KEY_LANG_RIGHT, new Gson().toJson(dir.second)).apply();
        });
    }

    @Override
    public Single<Pair<Lang, Lang>> getDir() {
        return Single.fromCallable(() -> {
            Lang langLeft = new Lang();
            Lang langRight = new Lang();
            langLeft.setCode(UI);
            langRight.setCode(UI.equals("ru") ? "en" : "ru");
            Lang langLeftFinal = new Gson().fromJson(
                    preferences.getString(KEY_LANG_LEFT, new Gson().toJson(langLeft)), Lang.class);
            Lang langRightFinal = new Gson().fromJson(
                    preferences.getString(KEY_LANG_RIGHT, new Gson().toJson(langRight)), Lang.class);
            return new Pair<>(langLeftFinal, langRightFinal);
        });
    }

    @Override
    public Completable putAutoDetectSetting(boolean isTurnedOn) {
        return Completable.fromAction(() -> preferences.edit().putBoolean(KEY_AUTO_DETECT_SETTING, isTurnedOn).commit());
    }

    @Override
    public Single<Boolean> getAutoDetectSetting() {
        return Single.fromCallable(() -> preferences.getBoolean(KEY_AUTO_DETECT_SETTING, false));
    }
}
