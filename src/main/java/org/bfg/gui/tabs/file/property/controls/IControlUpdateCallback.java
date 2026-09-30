package org.bfg.gui.tabs.file.property.controls;

/**
 * A callback for controls to handle changed values.
 *
 * @see IValueControl
 */
public interface IControlUpdateCallback {

    /**
     * Called when the control value is updated.
     *
     * @param control The control where the update originated from.
     */
    void onUpdate(IValueControl control);
}
