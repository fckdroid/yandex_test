package rxlll.yandextest.data.repositories.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public class PreferencesRepositoryImpl implements PreferencesRepository {
    private static final String PREFERENCES_NAME = "app-preferences";
    private static final String KEY_DIRS = "key_routes";
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
    public Completable putAutoDetectSetting(boolean isTurnedOn) {
        return Completable.fromAction(() -> preferences.edit().putBoolean(KEY_AUTO_DETECT_SETTING, isTurnedOn).commit());
    }

    @Override
    public Single<Boolean> getAutoDetectSetting() {
        return Single.fromCallable(() -> preferences.getBoolean(KEY_AUTO_DETECT_SETTING, false));
    }
}
