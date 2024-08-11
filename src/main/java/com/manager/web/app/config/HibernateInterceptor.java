package com.manager.web.app.config;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class HibernateInterceptor extends EmptyInterceptor {

    @Autowired
    private MeterRegistry meterRegistry;

    private Counter transactionFailureCounter;

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        // Check if the transaction has failed or was rolled back
        if (tx.getStatus() == TransactionStatus.ROLLED_BACK || tx.getStatus() == TransactionStatus.NOT_ACTIVE) {
            logMetricError(tx);
        }
    }

    private void logMetricError(Transaction tx) {
        if (transactionFailureCounter == null) {
            transactionFailureCounter = meterRegistry.counter("hibernate_transaction_failure");
        }
        transactionFailureCounter.increment();
        System.err.println("Transaction failed or was rolled back: " + tx.getStatus());
    }
}

