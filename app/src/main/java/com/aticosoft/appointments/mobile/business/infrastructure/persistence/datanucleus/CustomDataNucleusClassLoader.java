package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus;

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin.CustomPluginManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by rodrigo on 07/08/15.
 */
public class CustomDataNucleusClassLoader extends URLClassLoader {

    @Inject CustomDataNucleusClassLoader(CustomPluginManager customPluginManager) {
        super(urlListAsArray(customPluginManager.pluginUrls()));
    }

    private static URL[] urlListAsArray(List<URL> urlList) {
        return urlList.toArray(new URL[urlList.size()]);
    }

    @Override public InputStream getResourceAsStream(String resName) {
        return super.getResourceAsStream(resName);
    }

    @Override public Enumeration<URL> getResources(String resName) throws IOException {
        return super.getResources(resName);
    }

    @Override public URL getResource(String resName) {
        return super.getResource(resName);
    }
}
