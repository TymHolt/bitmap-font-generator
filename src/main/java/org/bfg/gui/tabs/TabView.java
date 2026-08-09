package org.bfg.gui.tabs;

import javax.swing.JPanel;
import java.awt.LayoutManager;

public abstract class TabView extends JPanel {

    public interface ITabPresenter {
        void doActionExport();
        void setShowGrid(boolean flag);
    }

    public TabView(LayoutManager layout) {
        super(layout);
    }

    public abstract ITabPresenter getPresenter();
}
