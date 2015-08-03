package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin;

import com.rodrigodev.common.file.PathUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by rodrigo on 14/08/15.
 */
@Value
@Accessors
public class PluginDirectory {

    private String path;

    public String join(String filePath) {
        return PathUtils.join(path, filePath);
    }
}
