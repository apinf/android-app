package uk.co.ribot.APInf_application.injection.component;

import dagger.Subcomponent;
import uk.co.ribot.APInf_application.injection.PerActivity;
import uk.co.ribot.APInf_application.injection.module.ActivityModule;
import uk.co.ribot.APInf_application.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
