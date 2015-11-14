package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus;

import org.datanucleus.ExecutionContext;
import org.datanucleus.enhancement.Persistable;
import org.datanucleus.metadata.AbstractClassMetaData;
import org.datanucleus.state.ReferentialStateManagerImpl;

/**
 * Created by Rodrigo Quesada on 16/11/15.
 */
public class CustomStateManager extends ReferentialStateManagerImpl {

    public CustomStateManager(ExecutionContext ec, AbstractClassMetaData cmd) {
        super(ec, cmd);
    }

    public Persistable getSavedImage() {
        return savedImage;
    }

    @Override
    public void clearSavedFields() {
        if (savedImage != null) {
            // Replace flags in order to avoid "NucleusException: Null LifeCycleState" when trying
            // to access savedImage later (let's say accessing savedImage is a hack, that's why)
            savedImage.dnReplaceFlags();
        }
        super.clearSavedFields();
    }
}
