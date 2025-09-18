package org.bfg.form.implement;

import org.bfg.form.base.LoadingForm;
import org.bfg.util.work.Work;

public final class WorkForm extends LoadingForm {

    public WorkForm(Work work) {
        super();
        setProgressMax(work.getTaskCount());

        work.addTaskFinishListener(taskIndex -> {
            final int progress = (taskIndex + 1);
            setText("Loading (" + progress + " / " + work.getTaskCount() + ")...");
            setProgress(progress);
            update();
        });

        work.addWorkFinishListener(this::dispose);
    }
}
