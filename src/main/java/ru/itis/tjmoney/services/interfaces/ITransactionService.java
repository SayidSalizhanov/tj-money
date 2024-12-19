package ru.itis.tjmoney.services.interfaces;

import jakarta.servlet.http.Part;
import ru.itis.tjmoney.dto.TransactionDTO;
import ru.itis.tjmoney.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ITransactionService {
    List<Transaction> getUserTransactions(int userId, String period);
    List<Transaction> getGroupTransactions(int groupId, String period);
    List<Transaction> getUserAndGroupTransactions(int userId, int groupId);
    List<TransactionDTO> getUserAndGroupTransactionDTOs(int userId, int groupId);
    List<Map<String, Integer>> getUserTransactionsGenerals(int userId, String period);
    List<Map<String, Integer>> getGroupTransactionsGenerals(int groupId, String period);
    TransactionDTO getTransactionDTO(int transactionId);
    void save(int userId, int groupId, int amount, String category, String type, LocalDateTime date, String description);
    void delete(int id);
    void update(int amount, String category, String type, String description, int id);
    void parseExcelToTransactions(Part filePart, int userId, int groupId);
}
