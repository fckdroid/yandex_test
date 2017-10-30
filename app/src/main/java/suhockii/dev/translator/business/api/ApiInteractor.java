package suhockii.dev.translator.business.api;

import android.util.Pair;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;
import suhockii.dev.translator.data.network.models.dictionary.Dictionary;
import suhockii.dev.translator.data.network.models.translator.Detect;
import suhockii.dev.translator.data.network.models.translator.Langs;
import suhockii.dev.translator.data.repositories.database.Lang;
import suhockii.dev.translator.data.repositories.database.Translation;

/** Created by Maksim Sukhotski on 4/14/2017. */

public interface ApiInteractor {

    Maybe<Translation> translate(String text,
                                 Pair<Lang, Lang> lang, String dirRequest);

    Single<Response<Detect>> detect(String text);

    Single<Response<Detect>> detect(String text,
                                    String hint);

    Maybe<Response<Langs>> getLangs(String ui);

    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui,
                                        String flags);

    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui);
}
