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
    private static final String KEY_ROUTES = "key_routes";

    private SharedPreferences preferences;

    public PreferencesRepositoryImpl(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
    }

    @Override
    public Completable putRoutes(Set<String> langs) {
        return Completable.fromAction(() -> preferences.edit().putStringSet(KEY_ROUTES, langs).commit());
    }

    @Override
    public Single<Set<String>> getRoutes() {
        return Single.fromCallable(() -> preferences.getStringSet(KEY_ROUTES, new HashSet<>()));
    }
}
