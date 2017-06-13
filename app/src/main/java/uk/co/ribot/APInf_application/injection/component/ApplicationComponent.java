package uk.co.ribot.APInf_application.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.APInf_application.data.DataManager;
import uk.co.ribot.APInf_application.data.SyncService;
import uk.co.ribot.APInf_application.data.local.DatabaseHelper;
import uk.co.ribot.APInf_application.data.local.PreferencesHelper;
import uk.co.ribot.APInf_application.data.remote.RibotsService;
import uk.co.ribot.APInf_application.injection.ApplicationContext;
import uk.co.ribot.APInf_application.injection.module.ApplicationModule;
import uk.co.ribot.APInf_application.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
