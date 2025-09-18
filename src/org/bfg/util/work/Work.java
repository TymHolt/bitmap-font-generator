package org.bfg.util.work;

import javax.swing.*;
import java.util.ArrayList;

public class Work {

    private final int taskCount;
    private final ArrayList<Runnable> workFinishListeners;
    private final SwingWorker<Void, Void> swingWorker;

    public Work(TaskIndexedRunnable taskHandler, int taskCount) {
        this.taskCount = taskCount;
        this.workFinishListeners = new ArrayList<>();
        this.swingWorker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                for (int taskIndex = 0; taskIndex < taskCount; taskIndex++) {
                    taskHandler.run(taskIndex);
                    setProgress((taskIndex + 1) * 100 / taskCount);
                }

                return null;
            }

            @Override
            protected void done() {
                super.done();

                for (Runnable workFinishListener : workFinishListeners)
                    workFinishListener.run();
            }
        };
    }

    public void executeAsync() {
        this.swingWorker.execute();
    }

    public int getTaskCount() {
        return this.taskCount;
    }

    public void addTaskFinishListener(TaskIndexedRunnable runnable) {
        this.swingWorker.addPropertyChangeListener(propertyChangeEvent -> {
            if ("progress".equals(propertyChangeEvent.getPropertyName()))
                runnable.run(((Integer) propertyChangeEvent.getNewValue()) * taskCount / 100);
        });
    }

    public void addWorkFinishListener(Runnable runnable) {
        this.workFinishListeners.add(runnable);
    }

    public interface TaskIndexedRunnable {

        void run(int taskIndex);
    }
}
