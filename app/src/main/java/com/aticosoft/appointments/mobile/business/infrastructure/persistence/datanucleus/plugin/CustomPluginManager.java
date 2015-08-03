package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin;

import android.content.res.AssetManager;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.rodrigodev.common.function.ThrowableMethodCallWithReturn;

import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import static com.rodrigodev.common.exception.ExceptionUtils.unchecked;

/**
 * Created by rodrigo on 07/08/15.
 */
@Singleton
@FieldDefaults(makeFinal = true)
public class CustomPluginManager {

    static class Services {

        @Inject PluginFactory pluginFactory;
        @Inject AssetManager assetManager;

        @Inject Services() {
        }
    }

    private Services s;
    private ImmutableList<Plugin> plugins;
    @NonFinal private Optional<ImmutableList<URL>> pluginUrls;

    @Inject CustomPluginManager(Services services) {
        this.s = services;
        this.plugins = createPlugins();
        this.pluginUrls = Optional.absent();
    }

    private ImmutableList<Plugin> createPlugins() {
        return unchecked(new ThrowableMethodCallWithReturn<ImmutableList<Plugin>>() {
            @Override public ImmutableList<Plugin> call() throws Throwable {
                ImmutableList.Builder<Plugin> plugins = ImmutableList.builder();
                for (String pluginDir : s.assetManager.list(Plugin.BASE_PATH)) {
                    plugins.add(s.pluginFactory.create(pluginDir));
                }
                return plugins.build();
            }
        });
    }

    public List<URL> pluginUrls() {

        if (!pluginUrls.isPresent()) {
            pluginUrls = Optional.of(ImmutableList.copyOf(Lists.transform(plugins, new Function<Plugin, URL>() {
                @Override public URL apply(Plugin plugin) {
                    return plugin.url();
                }
            })));
        }

        return pluginUrls.get();
    }
}
