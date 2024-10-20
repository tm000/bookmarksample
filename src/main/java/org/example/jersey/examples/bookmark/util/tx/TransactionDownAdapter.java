package org.example.jersey.examples.bookmark.util.tx;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

public class TransactionDownAdapter implements Transaction {
    jakarta.transaction.Transaction transaction;

    public TransactionDownAdapter(jakarta.transaction.Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void commit() throws RollbackException, HeuristicMixedException,
        HeuristicRollbackException, SecurityException, IllegalStateException,
        SystemException {
        try {
            transaction.commit();
        } catch (SecurityException | IllegalStateException | jakarta.transaction.RollbackException | jakarta.transaction.HeuristicMixedException
                | jakarta.transaction.HeuristicRollbackException | jakarta.transaction.SystemException e) {
            e.printStackTrace();
        }            
    }

    @Override
    public boolean delistResource(XAResource xaRes, int flag)
            throws IllegalStateException, SystemException {
        try {
            return transaction.delistResource(xaRes, flag);
        } catch (IllegalStateException | jakarta.transaction.SystemException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean enlistResource(XAResource xaRes) throws RollbackException,
                IllegalStateException, SystemException {
        try {
            return transaction.enlistResource(xaRes);
        } catch (IllegalStateException | jakarta.transaction.RollbackException | jakarta.transaction.SystemException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getStatus() throws SystemException {
        try {
            return transaction.getStatus();
        } catch (jakarta.transaction.SystemException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void registerSynchronization(javax.transaction.Synchronization sync) throws RollbackException,
                            IllegalStateException, SystemException {
        try {
            transaction.registerSynchronization(new SynchronizationAdapter(sync));
        } catch (IllegalStateException | jakarta.transaction.RollbackException | jakarta.transaction.SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() throws IllegalStateException, SystemException {
        try {
            transaction.rollback();
        } catch (IllegalStateException | jakarta.transaction.SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException {
        try {
            transaction.setRollbackOnly();
        } catch (IllegalStateException | jakarta.transaction.SystemException e) {
            e.printStackTrace();
        }            
    }    
}
