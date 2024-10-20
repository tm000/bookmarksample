package org.example.jersey.examples.bookmark.util.tx;

public class SynchronizationAdapter implements jakarta.transaction.Synchronization {
    javax.transaction.Synchronization sync;
    public SynchronizationAdapter(javax.transaction.Synchronization sync) {
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
