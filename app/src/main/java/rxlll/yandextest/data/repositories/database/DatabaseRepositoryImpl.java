package rxlll.yandextest.data.repositories.database;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import rxlll.yandextest.App;
import rxlll.yandextest.data.database.LangDao;
import rxlll.yandextest.data.database.TranslationDao;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class DatabaseRepositoryImpl implements DatabaseRepository {

    @Inject
    LangDao langDao;

    @Inject
    TranslationDao translationDao;

    public DatabaseRepositoryImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Completable putLangs(Map<String, String> langs) {
        return Completable.fromAction(() -> {
            for (Map.Entry<String, String> entry : langs.entrySet()) {
                Lang lang = new Lang();
                lang.setCode(entry.getKey());
                lang.setDescription(entry.getValue());
                langDao.insertOrReplace(lang);
            }
        });
    }

    @Override
    public Single<List<Lang>> getLangs() {
        return Single.fromCallable(() -> langDao.loadAll());
    }
}
