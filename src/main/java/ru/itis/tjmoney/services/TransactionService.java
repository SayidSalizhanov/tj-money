package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.TransactionDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.dto.TransactionDTO;
import ru.itis.tjmoney.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final UserDAO userDAO;

    public TransactionService(TransactionDAO transactionDAO, UserDAO userDAO) {
        this.transactionDAO = transactionDAO;
        this.userDAO = userDAO;
    }

    public List<Transaction> getUserTransactions(int userId) {
        return transactionDAO.findUserTransactions(userId);
    }

    public List<Transaction> getGroupTransactions(int groupId) {
        return transactionDAO.findGroupTransactions(groupId);
    }

    public List<Transaction> getUserAndGroupTransactions(int userId, int groupId) {
        return groupId == 0 ? transactionDAO.findUserTransactions(userId) : transactionDAO.findGroupTransactions(groupId);
    }

    public List<TransactionDTO> getUserAndGroupTransactionDTOs(int userId, int groupId) {
        return getUserAndGroupTransactions(userId, groupId).stream()
                .map(t -> new TransactionDTO(t.getId(), t.getAmount(), t.getCategory(), t.getType(), userDAO.findById(t.getUserId()).getUsername(), t.getDescription(), t.getDateTime().toString()))
                .toList();
    }

    public TransactionDTO getTransactionDTO(int transactionId) {
        Transaction transaction = transactionDAO.findTransactionById(transactionId);
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getType(),
                userDAO.findById(transaction.getUserId()).getUsername(),
                transaction.getDescription(),
                transaction.getDateTime().toString()
        );
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
