package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Transaction;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final String FIND_USERS_TRANSACTIONS_SQL = "SELECT * FROM Transactions WHERE user_id = ? AND group_id = null";
    private static final String FIND_GROUP_TRANSACTIONS_SQL = "SELECT * FROM Transactions WHERE user_id = null AND group_id = ?";

    public List<Transaction> findUserTransactions(int userId) {
        return getTransactions(userId, FIND_USERS_TRANSACTIONS_SQL);
    }

    public List<Transaction> findGroupTransactions(int groupId) {
        return getTransactions(groupId, FIND_GROUP_TRANSACTIONS_SQL);
    }

    private List<Transaction> getTransactions(int parameter, String sql) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(
                        new Transaction(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getInt("amount"),
                                resultSet.getString("category"),
                                resultSet.getString("type"),
                                resultSet.getTimestamp("dateTime").toLocalDateTime(),
                                resultSet.getString("description")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return transactions;
    }
}
