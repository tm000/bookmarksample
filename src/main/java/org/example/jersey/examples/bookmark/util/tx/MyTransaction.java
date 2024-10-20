package org.example.jersey.examples.bookmark.util.tx;

import javax.naming.RefAddr;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import com.atomikos.icatch.jta.UserTransactionImp;

public class MyTransaction extends javax.naming.Reference implements UserTransaction, jakarta.transaction.UserTransaction {

    UserTransactionImp utm = new UserTransactionImp();

    public MyTransaction(String className, String factory, String factoryLocation) {
        super(className, factory, factoryLocation);
    }

    public MyTransaction(String className, RefAddr addr, String factory, String factoryLocation) {
        super(className, addr, factory, factoryLocation);
    }

    public MyTransaction(String className, RefAddr addr) {
        super(className, addr);
    }

    public MyTransaction(String className) {
        super(className);
    }

    public MyTransaction() throws SystemException {
        super("UserTransaction");
    }

    @Override
    public void begin() {
        try {
            utm.begin();
        } catch (NotSupportedException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commit() {
        try {
            utm.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException |
                SecurityException | IllegalStateException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            utm.rollback();
        } catch (IllegalStateException | SecurityException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRollbackOnly() {
        try {
            utm.setRollbackOnly();
        } catch (IllegalStateException | SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getStatus() {
        try {
            return utm.getStatus();
        } catch (SystemException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void setTransactionTimeout(int seconds) {
        try {
            utm.setTransactionTimeout(seconds);
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }
}
