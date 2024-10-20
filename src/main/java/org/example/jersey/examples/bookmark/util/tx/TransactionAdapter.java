package org.example.jersey.examples.bookmark.util.tx;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

public class TransactionAdapter implements jakarta.transaction.Transaction {
    Transaction transaction;

    public TransactionAdapter(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void commit() throws jakarta.transaction.RollbackException, jakarta.transaction.HeuristicMixedException,
            jakarta.transaction.HeuristicRollbackException, SecurityException, IllegalStateException,
            jakarta.transaction.SystemException {
        try {
            transaction.commit();
        } catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
                | HeuristicRollbackException | SystemException e) {
            e.printStackTrace();
        }            
    }

    @Override
    public boolean delistResource(XAResource xaRes, int flag)
            throws IllegalStateException, jakarta.transaction.SystemException {
        try {
            return transaction.delistResource(xaRes, flag);
        } catch (IllegalStateException | SystemException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean enlistResource(XAResource xaRes) throws jakarta.transaction.RollbackException,
            IllegalStateException, jakarta.transaction.SystemException {
        try {
            return transaction.enlistResource(xaRes);
        } catch (IllegalStateException | RollbackException | SystemException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getStatus() throws jakarta.transaction.SystemException {
        try {
            return transaction.getStatus();
        } catch (SystemException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void registerSynchronization(jakarta.transaction.Synchronization sync) throws jakarta.transaction.RollbackException,
            IllegalStateException, jakarta.transaction.SystemException {
        try {
            transaction.registerSynchronization(new SynchronizationDownAdapter(sync));
        } catch (IllegalStateException | RollbackException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() throws IllegalStateException, jakarta.transaction.SystemException {
        try {
            transaction.rollback();
        } catch (IllegalStateException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException, jakarta.transaction.SystemException {
        try {
            transaction.setRollbackOnly();
        } catch (IllegalStateException | SystemException e) {
            e.printStackTrace();
        }            
    }    
}
