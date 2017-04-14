package llltt.yandextest.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import llltt.yandextest.dagger.modules.DataModule;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Component(modules = {DataModule.class})
@Singleton
public interface AppComponent {
}
