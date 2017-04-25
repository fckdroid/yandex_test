package rxlll.yandextest.data.repositories.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.data.network.models.translator.Langs;

import static rxlll.yandextest.App.LOG_TAG;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class DatabaseRepositoryImpl implements DatabaseRepository {

    @Inject
    LangDao langDao;

    @Inject
    TranslationDao translationDao;

    private List<String> lovelyLangs;

    public DatabaseRepositoryImpl() {
        App.appComponent.inject(this);
        lovelyLangs = new ArrayList<>();
        lovelyLangs.add(Locale.getDefault().getLanguage());
        lovelyLangs.add("en");
        lovelyLangs.add("de");
    }

    @Override
    public Maybe<Response<Langs>> putLangs(Response<Langs> langs) {
        return Maybe.fromCallable(() -> {
            for (Map.Entry<String, String> entry : langs.body().getLangs().entrySet()) {
                Lang lang = new Lang();
                lang.setCode(entry.getKey());
                lang.setDescription(entry.getValue());
                if (lovelyLangs.contains(lang.getCode())) lang.setRating(1);
                langDao.insertOrReplace(lang);

            }
            Log.d(LOG_TAG, "Языки сохранены в БД");
            langs.body().setLangs(langDao.loadAll());
            Log.d(LOG_TAG, "Языки считаны из БД и дополнили response body");
            Log.d(LOG_TAG, "---------------------------------------------");

            return langs;
        });
    }

    @Override
    public Single<List<Lang>> getLangs() {
        return Single.fromCallable(() ->
                langDao.queryBuilder().orderDesc(LangDao.Properties.Rating).list());
    }

    @Override
    public Completable putTranslation(Translation translation) {
        return Completable.fromAction(() -> translationDao.insertOrReplace(translation))
                .doOnComplete(() -> Log.d(LOG_TAG, "Перевод записан в БД"));
    }

    @Override
    public Single<List<Translation>> getTranslations(boolean favorites) {
        return Single.fromCallable(() -> {
            if (favorites) return translationDao.queryBuilder()
                    .where(TranslationDao.Properties.IsFavorite.eq(true)).list();
            return translationDao.queryBuilder().list();
        });
    }

    @Override
    public Single<Translation> getTranslation(String text, String lang) {
        return Single.fromCallable(() -> {
            Translation unique = translationDao.queryBuilder().
                    where(TranslationDao.Properties.Original.eq(text),
                            TranslationDao.Properties.Direction.eq(lang)).unique();
            if (unique == null) {
                Log.d(LOG_TAG, "В БД нет такого перевода");
                return new Translation();

            } else {
                Log.d(LOG_TAG, "Перевод прочитан из БД");
                return unique;
            }
        });
    }

    @Override
    public Completable deleteAll() {
        return Completable.fromAction(() -> translationDao.deleteAll());
    }
}
