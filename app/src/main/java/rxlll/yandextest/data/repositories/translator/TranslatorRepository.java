package rxlll.yandextest.data.repositories.translator;

import io.reactivex.Single;
import rxlll.yandextest.data.network.models.BaseResponse;
import rxlll.yandextest.data.network.models.Detect;
import rxlll.yandextest.data.network.models.Langs;
import rxlll.yandextest.data.network.models.Translate;

/** Created by Maksim Sukhotski on 4/14/2017. */

public interface TranslatorRepository {

    Single<BaseResponse<Translate>> translate(String text,
                                              String lang);

    Single<BaseResponse<Translate>> translate(String text,
                                              String lang,
                                              String options);

    Single<BaseResponse<Detect>> detect(String text);

    Single<BaseResponse<Detect>> detect(String text,
                                        String hint);

    Single<Langs> getLangs(String ui);
}
