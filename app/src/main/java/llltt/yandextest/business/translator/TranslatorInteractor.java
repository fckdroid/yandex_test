package llltt.yandextest.business.translator;

import io.reactivex.Single;
import llltt.yandextest.data.network.models.BaseResponse;
import llltt.yandextest.data.network.models.Detect;
import llltt.yandextest.data.network.models.Translate;

/** Created by Maksim Sukhotski on 4/14/2017. */

interface TranslatorInteractor {

    Single<BaseResponse<Translate>> translate(String text,
                                              String lang);

    Single<BaseResponse<Translate>> translate(String text,
                                              String lang,
                                              String options);

    Single<BaseResponse<Detect>> detect(String text);

    Single<BaseResponse<Detect>> detect(String text,
                                        String hint);

    Single<BaseResponse<Detect>> getLangs(String ui);
}
