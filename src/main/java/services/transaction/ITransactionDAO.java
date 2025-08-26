package services.transaction;

import domains.Transaction;

import java.util.List;

public interface ITransactionDAO {
    List<Transaction> findByAccountId(int accountId);
    void save(Transaction transaction);
    int delete(int id);
}

