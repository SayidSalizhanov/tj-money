package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.Transaction;

import java.util.List;

public interface ITransactionDAO {
    List<Transaction> findUserTransactions(int userId, String period);
    List<Transaction> findGroupTransactions(int groupId, String period);
    Transaction findTransactionById(int transactionId);
    Transaction save(Transaction transaction);
    void update(Transaction updatedTransaction);
    void deleteById(int id);
}
