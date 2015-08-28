package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin;

import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;

import com.aticosoft.appointments.mobile.business.BuildConfig;
import com.rodrigodev.common.function.ThrowableMethodCallWithReturn;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import static com.rodrigodev.common.exception.ExceptionUtils.unchecked;
import static com.rodrigodev.common.file.PathUtils.join;

/**
 * Created by rodrigo on 07/08/15.
 */
@FieldDefaults(makeFinal = true)
@Accessors
public class Plugin {

    public static String BASE_PATH = "plugins";

    private static String PLUGIN_FILE_PATH = "plugin.xml";
    private static String MANIFEST_PATH = "META-INF/MANIFEST.MF";

    static class Services {

        @Inject ApplicationInfo applicationInfo;
        @Inject AssetManager assetManager;

        @Inject Services() {
        }
    }

    private Services s;
    private String id;
    private PluginDirectory sourceDir;
    private PluginDirectory targetDir;
    @Getter private URL url;

    Plugin(Services services, String id) {
        this.s = services;
        this.id = id;
        this.sourceDir = initSourceDir();
        this.targetDir = initTargetDir();
        copySourceFiles();
        // Assign url value after files have been moved and directories created
        this.url = initUrl();
    }

    private PluginDirectory initSourceDir() {
        return new PluginDirectory(join(BASE_PATH, id));
    }

    private PluginDirectory initTargetDir() {
        return new PluginDirectory(join(
                s.applicationInfo.dataDir, BASE_PATH, String.valueOf(BuildConfig.VERSION_CODE), id
        ));
    }

    public URL initUrl() {
        return unchecked(
                new ThrowableMethodCallWithReturn<URL>() {
                    @Override public URL call() throws Throwable {
                        return new File(targetDir.path()).toURI().toURL();
                    }
                }
        );
    }

    private void copySourceFiles() {
        copyFile(PLUGIN_FILE_PATH);
        copyFile(MANIFEST_PATH);
    }

    private void copyFile(String filePath) {
        File outputFile = new File(targetDir.join(filePath));
        if (!outputFile.exists()) {

            InputStream input = null;
            FileOutputStream output = null;
            try {
                input = s.assetManager.open(sourceDir.join(filePath));

                outputFile.getParentFile().mkdirs();
                output = new FileOutputStream(outputFile);

                IOUtils.copy(input, output);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
        }
    }
}
