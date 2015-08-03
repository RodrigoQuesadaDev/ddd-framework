package com.rodrigodev.common.file;

import java.io.File;

import static org.apache.commons.io.FilenameUtils.normalizeNoEndSeparator;

/**
 * Created by rodrigo on 14/08/15.
 */
public class PathUtils {

    public static String join(String... paths) {

        StringBuilder joinedPath = new StringBuilder();
        if (paths.length > 0) {
            joinedPath.append(normalizeNoEndSeparator(paths[0]));
            for (int i = 1; i < paths.length; i++) {
                joinedPath.append(File.separatorChar);
                joinedPath.append(normalizeNoEndSeparator(paths[i]));
            }
        }

        return joinedPath.toString();
    }
}
