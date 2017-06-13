package uk.co.ribot.APInf_application.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.APInf_application.injection.component.ApplicationComponent;
import uk.co.ribot.APInf_application.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
