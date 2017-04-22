package rxlll.yandextest.data.repositories.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import rxlll.yandextest.App;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class DatabaseRepositoryImpl implements DatabaseRepository {

    @Inject
    LangDao langDao;

    @Inject
    TranslationDao translationDao;
    List<String> lovelyLangs;

    public DatabaseRepositoryImpl() {
        App.appComponent.inject(this);
        lovelyLangs = new ArrayList<>();
        lovelyLangs.add(Locale.getDefault().getLanguage());
        lovelyLangs.add("en");
        lovelyLangs.add("de");
    }

    @Override
    public Completable putLangs(Map<String, String> langs) {
        return Completable.fromAction(() -> {
            for (Map.Entry<String, String> entry : langs.entrySet()) {
                Lang lang = new Lang();
                lang.setCode(entry.getKey());
                lang.setDescription(entry.getValue());
                if (lovelyLangs.contains(lang.getCode())) {
                    lang.setRating(1);
                }
                langDao.insertOrReplace(lang);
            }
        });
    }

    @Override
    public Single<List<Lang>> getLangs() {
        return Single.fromCallable(() -> langDao.queryBuilder().orderDesc(LangDao.Properties.Rating).list());
    }
}
