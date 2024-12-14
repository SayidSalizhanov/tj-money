package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.TransactionDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.dto.TransactionDTO;
import ru.itis.tjmoney.models.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .map(t -> new TransactionDTO(
                        t.getId(),
                        t.getAmount(),
                        t.getCategory(),
                        t.getType(),
                        userDAO.findById(t.getUserId()).getUsername(),
                        t.getDescription(),
                        t.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .toList();
    }

    public List<Map<String, Integer>> getUserTransactionsGenerals(int userId) {
        List<Transaction> transactions = getUserTransactions(userId);
        return getTransactionsGeneralsMaps(transactions);
    }

    public List<Map<String, Integer>> getGroupTransactionsGenerals(int groupId) {
        List<Transaction> transactions = getGroupTransactions(groupId);
        return getTransactionsGeneralsMaps(transactions);
    }

    private List<Map<String, Integer>> getTransactionsGeneralsMaps(List<Transaction> transactions) {
        Map<String, Integer> mapOfIncomeTransactions = new HashMap<>();
        Map<String, Integer> mapOfExpenseTransactions = new HashMap<>();

        mapOfIncomeTransactions.put("Заработная плата", 0);
        mapOfIncomeTransactions.put("Прибыль от бизнеса", 0);
        mapOfIncomeTransactions.put("Дивиденды", 0);
        mapOfIncomeTransactions.put("Аренда", 0);
        mapOfIncomeTransactions.put("Премии и бонусы", 0);
        mapOfIncomeTransactions.put("Интересы", 0);
        mapOfIncomeTransactions.put("Пенсии и пособия", 0);
        mapOfIncomeTransactions.put("Другое", 0);

        mapOfExpenseTransactions.put("Еда и напитки", 0);
        mapOfExpenseTransactions.put("Транспорт", 0);
        mapOfExpenseTransactions.put("Жилье", 0);
        mapOfExpenseTransactions.put("Развлечения", 0);
        mapOfExpenseTransactions.put("Одежда", 0);
        mapOfExpenseTransactions.put("Здоровье", 0);
        mapOfExpenseTransactions.put("Образование", 0);
        mapOfExpenseTransactions.put("Другое", 0);

        for (Transaction transaction : transactions) {
            if (transaction.getType().equalsIgnoreCase("Доход")) {
                mapOfIncomeTransactions.merge(transaction.getCategory(), transaction.getAmount(), Integer::sum);
            }
            else {
                mapOfExpenseTransactions.merge(transaction.getCategory(), transaction.getAmount(), Integer::sum);
            }
        }

        List<Map<String, Integer>> maps = new ArrayList<>();
        maps.add(mapOfIncomeTransactions);
        maps.add(mapOfExpenseTransactions);

        return maps;
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
                transaction.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
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
