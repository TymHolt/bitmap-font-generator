package org.bfg.gui.tabs.file.property.controls;

/**
 * An abstract control that can deliver a generic value.
 */
public interface IValueControl {

    /**
     * The generic value of this control.
     *
     * @return The control value as an {@link Object}.
     */
    Object getControlValue();
}
