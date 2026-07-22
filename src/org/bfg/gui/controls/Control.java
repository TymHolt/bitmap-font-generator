package org.bfg.gui.controls;

import javax.swing.*;
import java.util.Objects;

abstract class Control {

    protected final Runnable onAction;
    private boolean hasChangedFlag;

    Control(Runnable onAction) {
        Objects.requireNonNull(onAction);
        this.onAction = onAction;
        this.hasChangedFlag = false;
    }

    final boolean hasChanged() {
        return this.hasChangedFlag;
    }

    final void setHasChanged(boolean hasChangedFlag) {
        this.hasChangedFlag = hasChangedFlag;
    }

    abstract JComponent getComponent();
    abstract void readComponentValue();
}
