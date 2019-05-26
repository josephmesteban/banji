package com.josephmesteban.banji.dependencyinjectors;

import com.josephmesteban.banji.MainActivity;
import com.josephmesteban.banji.ui.PlaceholderFragment;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(PlaceholderFragment placeholderFragment);

}
