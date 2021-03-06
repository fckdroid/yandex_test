package suhockii.dev.translator.data.repositories.dictionary;

import io.reactivex.Single;
import retrofit2.Response;
import suhockii.dev.translator.data.network.models.dictionary.Dictionary;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public interface DictionaryRepository {
    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui,
                                        String flags);

    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui);
}
