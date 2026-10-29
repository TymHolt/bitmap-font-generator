package org.bfg.gui.tabs.file.property.controls;

import java.util.HashMap;
import java.util.Objects;

/**
 * An implementation of {@link IControlUpdateCallback}, that can be added to multiple controls. It stores the
 * corresponding values of each control and can notify a listener when one of them changes. This reduces the overhead
 * of storing all values for each control in a form.
 */
public final class ControlValueChangeObserver implements IControlUpdateCallback {

    private final Runnable onChangeCallback;
    private final HashMap<IValueControl, Object> controlValueCache = new HashMap<>();

    /**
     * Create the callback with the given {@link Runnable} to be called when a value changes.
     *
     * @param onChangeCallback The {@link Runnable} to call when a value changes, must not be {@code null}.
     */
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
