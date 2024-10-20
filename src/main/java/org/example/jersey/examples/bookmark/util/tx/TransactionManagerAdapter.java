package org.example.jersey.examples.bookmark.util.tx;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import java.lang.IllegalStateException;
import java.lang.SecurityException;


public class TransactionManagerAdapter implements jakarta.transaction.TransactionManager {
    javax.transaction.TransactionManager manager;

    public TransactionManagerAdapter(javax.transaction.TransactionManager manager) {
        this.manager = manager;
    }

    @Override
    public void begin() {
        try {
            this.manager.begin();
        } catch (NotSupportedException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commit() {
        try {
            this.manager.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException |
                SecurityException | IllegalStateException | SystemException e) {
            e.printStackTrace();
        }           
    }

    @Override
    public int getStatus() {
        try {
            return this.manager.getStatus();
        } catch (SystemException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public jakarta.transaction.Transaction getTransaction() {
        try {
            Transaction tran = this.manager.getTransaction();
            return tran == null ? null : new TransactionAdapter(tran);
        } catch (SystemException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void resume(jakarta.transaction.Transaction tobj) {
        try {
            this.manager.resume(new TransactionDownAdapter(tobj));
        } catch (InvalidTransactionException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            this.manager.rollback();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRollbackOnly() {
        try {
            this.manager.setRollbackOnly();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTransactionTimeout(int seconds) {
        try {
            this.manager.setTransactionTimeout(seconds);
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public jakarta.transaction.Transaction suspend() {
        try {
            return new TransactionAdapter(this.manager.suspend());
        } catch (SystemException e) {
            e.printStackTrace();
            return null;
        }
    }
}
