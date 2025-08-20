package services;

import domains.Transaction;

import java.util.List;

public interface ITransactionDAO {
    Transaction findById(int id);
    List<Transaction> findByAccountId(int accountId);
    void save(Transaction transaction);
    void delete(int id);
}

