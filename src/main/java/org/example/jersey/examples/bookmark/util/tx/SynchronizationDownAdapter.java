package org.example.jersey.examples.bookmark.util.tx;

import jakarta.transaction.Synchronization;

public class SynchronizationDownAdapter implements javax.transaction.Synchronization {
    Synchronization sync;
    public SynchronizationDownAdapter(Synchronization sync) {
        this.sync = sync;
    }

    @Override
    public void beforeCompletion() {
        sync.beforeCompletion();
    }

    @Override
    public void afterCompletion(int var1) {
        sync.afterCompletion(var1);
    }
}
