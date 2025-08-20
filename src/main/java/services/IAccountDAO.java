package services;

import domains.Account;

import java.util.List;

public interface IAccountDAO {
    Account findById(int id);
    List<Account> findByName(String name);
    Account findByPhone(String phone);
    List<Account> findAll();
    void save(Account account);
    void update(Account account);
    void delete(int id);
}
