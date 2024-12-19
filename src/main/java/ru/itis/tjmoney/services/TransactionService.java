package ru.itis.tjmoney.services;

import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.itis.tjmoney.dao.TransactionDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.dto.ExcelParseTransactionDTO;
import ru.itis.tjmoney.dto.TransactionDTO;
import ru.itis.tjmoney.exceptions.ExcelParseException;
import ru.itis.tjmoney.models.Transaction;

import java.io.InputStream;
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

    public List<Transaction> getUserTransactions(int userId, String period) {
        return transactionDAO.findUserTransactions(userId, period);
    }

    public List<Transaction> getGroupTransactions(int groupId, String period) {
        return transactionDAO.findGroupTransactions(groupId, period);
    }

    public List<Transaction> getUserAndGroupTransactions(int userId, int groupId) {
        return groupId == 0 ? transactionDAO.findUserTransactions(userId, "all") : transactionDAO.findGroupTransactions(groupId, "all");
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

    public List<Map<String, Integer>> getUserTransactionsGenerals(int userId, String period) {
        List<Transaction> transactions = getUserTransactions(userId, period);
        return getTransactionsGeneralsMaps(transactions);
    }

    public List<Map<String, Integer>> getGroupTransactionsGenerals(int groupId, String period) {
        List<Transaction> transactions = getGroupTransactions(groupId, period);
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
    }

    public void parseExcelToTransactions(Part filePart, int userId, int groupId) {
        String fileName = filePart.getSubmittedFileName();

        List<ExcelParseTransactionDTO> transactionDTOs = new ArrayList<>();

        if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            try (InputStream fis = filePart.getInputStream();
                 XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

                var sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null && row.getCell(0) != null) {
                        int amount = (int) row.getCell(0).getNumericCellValue();
                        if (amount > 1000000 || amount < 0) throw new ExcelParseException("Стоимость не должна превышать 1000000");
                        String type = row.getCell(1).getStringCellValue();
                        String category = row.getCell(2).getStringCellValue();
                        LocalDateTime date = row.getCell(3).getLocalDateTimeCellValue();
                        String description = row.getCell(4).getStringCellValue();

                        transactionDTOs.add(new ExcelParseTransactionDTO(amount, type, category, date, description));
                    }
                }
            } catch (Exception e) {
                throw new ExcelParseException(e.getMessage());
            }
        } else {
            throw new ExcelParseException("Тип файла не известен");
        }

        for (ExcelParseTransactionDTO dto : transactionDTOs) {
            save(userId, groupId, dto.getAmount(), dto.getCategory(), dto.getType(), dto.getDate(), dto.getDescription());
        }
    }
}
