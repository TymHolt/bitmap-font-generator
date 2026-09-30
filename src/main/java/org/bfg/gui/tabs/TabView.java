package org.bfg.gui.tabs;

import javax.swing.JPanel;
import java.awt.LayoutManager;

/**
 * Base for all tabs.
 */
public abstract class TabView extends JPanel {

    /**
     * Base for all tab presenters. These actions may need to be invoked from the GUI.
     */
    public interface ITabPresenter {
        /**
         * Called by the GUI when the export action was requested.
         */
        void doActionExport();

        /**
         * Called by the GUI when the show grid flag changed.
         *
         * @param flag The flag state.
         */
        void setShowGrid(boolean flag);

        /**
         * Called by the GUI when the show UV coordinates flag changed.
         *
         * @param flag The flag state.
         */
        void setShowUvCoordinates(boolean flag);
    }

    /**
     * Initialize the tab with the given {@link LayoutManager}.
     *
     * @param layout The layout to use.
     */
    public TabView(LayoutManager layout) {
        super(layout);
    }

    /**
     * @return The presenter of this tab, so the GUI can invoke events. Intended to not be {@code null}.
     */
    public abstract ITabPresenter getPresenter();
}
