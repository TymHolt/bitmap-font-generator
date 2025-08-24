package org.bfg.form.implement;

import org.bfg.form.base.LoadingForm;
import org.bfg.form.util.FormUpdateService;
import org.bfg.generate.IWork;

public final class WorkForm extends LoadingForm {

    private final IWork work;

    public WorkForm(IWork work) {
        super();

        this.work = work;
        new FormUpdateService(this).start();
    }

    @Override
    protected void onUpdate() {
        if (this.work.hasFinished()) {
            this.dispose();
            return;
        }

        final int max = this.work.getTaskCount();
        final int progress = this.work.getFinishedTaskCount();

        this.setText("Loading (" + progress + " / " + max + ")...");
        this.setProgressMax(max);
        this.setProgress(progress);
    }
}
