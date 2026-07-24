package org.bfg.gui.tabs.file.property.controls;

import java.util.HashMap;
import java.util.Objects;

public final class ControlValueChangeObserver implements IControlUpdateCallback {

    private final Runnable onChangeCallback;
    private final HashMap<IValueControl, Object> controlValueCache = new HashMap<>();

    public ControlValueChangeObserver(Runnable onChangeCallback) {
        Objects.requireNonNull(onChangeCallback);
        this.onChangeCallback = onChangeCallback;
    }

    @Override
    public void onUpdate(IValueControl control) {
        final Object controlValue = control.getControlValue();
        if (!this.controlValueCache.containsKey(control) ||
            differentValues(controlValue, controlValueCache.get(control))) {
            this.controlValueCache.put(control, controlValue);
            this.onChangeCallback.run();
        }
    }

    private static boolean differentValues(Object valueA, Object valueB) {
        if (valueA == null || valueB == null)
            return valueA != valueB;

        return !valueA.equals(valueB);
    }
}
