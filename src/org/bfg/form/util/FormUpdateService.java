package org.bfg.form.util;

import org.bfg.form.base.Form;

public final class FormUpdateService extends Thread {

    public FormUpdateService(Form form) {
        super(() -> {
            while (!form.isVisible()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }

            while (form.isVisible()) {
                form.update();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        });
    }
}
