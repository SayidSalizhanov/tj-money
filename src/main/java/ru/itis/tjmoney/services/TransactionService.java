package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.TransactionDAO;
import ru.itis.tjmoney.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public List<Transaction> getUserTransactions(int userId) {
        return transactionDAO.findUserTransactions(userId);
    }

    public List<Transaction> getGroupTransactions(int groupId) {
        return transactionDAO.findGroupTransactions(groupId);
    }

    public List<Transaction> getUserAndGroupTransactions(int userId, int groupId) {
        return groupId == 0 ? transactionDAO.findUserTransactions(userId) : transactionDAO.findUserAndGroupTransactions(userId, groupId);
    }

    public Transaction getTransaction(int transactionId) {
        return transactionDAO.findTransactionById(transactionId);
    }

    public void save(int userId, int groupId, int amount, String category, String type, LocalDateTime date, String description) {
        transactionDAO.save(new Transaction(
                0,
                userId,
                groupId,
                amount,
                category,
                type,
                date,
                description
        ));
    }

    public void delete(int id) {
        transactionDAO.deleteById(id);
    }

    public void update(int amount, String category, String type, String description, int id) {
        transactionDAO.update(new Transaction(id, 0, 0, amount, category, type, null, description));

        // здесь должна быть какая-то проверка
    }
}
