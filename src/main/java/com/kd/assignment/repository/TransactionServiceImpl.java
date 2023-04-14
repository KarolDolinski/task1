package com.kd.assignment.repository;

import com.kd.assignment.exception.TransactionException;
import com.kd.assignment.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kd.assignment.util.UserMessages.TRANSACTION_EXCEPTION_NOTHING_TO_ROLLBACK_MSG;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl {
    private List<Transaction> transactionList = new ArrayList<>();

    private final DataRepository dataRepository;

    public void begin() {
        if (isTransactionInProgress()) {
            transactionList.add(new Transaction(new HashMap<>(getLastTransaction().getTransactionDb())));
        } else {
            transactionList.add(new Transaction(new HashMap<>(dataRepository.getDb())));
        }
    }

    public void rollback() throws TransactionException {
        if (!isTransactionInProgress()) {
            throw new TransactionException(TRANSACTION_EXCEPTION_NOTHING_TO_ROLLBACK_MSG);
        }
        transactionList.remove(transactionList.size()-1);
    }

    public void commit() {
        dataRepository.setDb(getLastTransaction().getTransactionDb());
        transactionList.clear();
    }

    public boolean isTransactionInProgress() {
        return transactionList.size() > 0;
    }


    private Transaction getLastTransaction() {
        return transactionList.get(transactionList.size() - 1);
    }

    public Map<String, String> getCurrentDb() {
        if (isTransactionInProgress()) {
            return getLastTransaction().getTransactionDb();
        }
        return dataRepository.getDb();
    }
}
