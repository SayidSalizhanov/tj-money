package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.TransactionDAO;
import ru.itis.tjmoney.models.Transaction;

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
}
