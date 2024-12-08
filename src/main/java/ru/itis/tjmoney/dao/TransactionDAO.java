package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Transaction;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final String FIND_USER_TRANSACTIONS_SQL = "SELECT * FROM Transactions WHERE user_id = ? AND group_id IS NULL";
    private static final String FIND_GROUP_TRANSACTIONS_SQL = "SELECT * FROM Transactions WHERE group_id = ?";
    private static final String FIND_USER_AND_GROUP_TRANSACTIONS_SQL = "SELECT * FROM Transactions WHERE user_id = ? AND group_id = ?";
    private static final String FIND_TRANSACTION_BY_ID_SQL = "SELECT * FROM Transactions WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Transactions (user_id, group_id, amount, category, type, date_time, description) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM Transactions WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE Transactions SET amount = ?, category = ?, type = ?, description = ? WHERE id = ?";

    public List<Transaction> findUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_TRANSACTIONS_SQL)) {
            statement.setInt(1, userId);
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
                                resultSet.getTimestamp("date_time").toLocalDateTime(),
                                resultSet.getString("description")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return transactions;
    }

    public List<Transaction> findGroupTransactions(int groupId) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_GROUP_TRANSACTIONS_SQL)) {
            statement.setInt(1, groupId);
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
                                resultSet.getTimestamp("date_time").toLocalDateTime(),
                                resultSet.getString("description")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return transactions;
    }

    public List<Transaction> findUserAndGroupTransactions(int userId, int groupId) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_AND_GROUP_TRANSACTIONS_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, groupId);

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
                                resultSet.getTimestamp("date_time").toLocalDateTime(),
                                resultSet.getString("description")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return transactions;
    }

    public Transaction findTransactionById(int transactionId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TRANSACTION_BY_ID_SQL)) {
            statement.setInt(1, transactionId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Transaction(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("group_id"),
                        resultSet.getInt("amount"),
                        resultSet.getString("category"),
                        resultSet.getString("type"),
                        resultSet.getTimestamp("date_time").toLocalDateTime(),
                        resultSet.getString("description")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Transaction save(Transaction transaction) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, transaction.getUserId());
            if (transaction.getGroupId() == 0) {
                statement.setNull(2, java.sql.Types.INTEGER);
            } else {
                statement.setInt(2, transaction.getGroupId());
            }
            statement.setInt(3, transaction.getAmount());
            statement.setString(4, transaction.getCategory());
            statement.setString(5, transaction.getType());
            statement.setTimestamp(6, Timestamp.valueOf(transaction.getDateTime()));
            statement.setString(7, transaction.getDescription());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);

            return new Transaction(
                    id,
                    transaction.getUserId(),
                    transaction.getGroupId(),
                    transaction.getAmount(),
                    transaction.getCategory(),
                    transaction.getType(),
                    transaction.getDateTime(),
                    transaction.getDescription()
            );
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void update(Transaction updatedTransaction) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, updatedTransaction.getAmount());
            statement.setString(2, updatedTransaction.getCategory());
            statement.setString(3, updatedTransaction.getType());
            statement.setString(4, updatedTransaction.getDescription());
            statement.setInt(5, updatedTransaction.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void deleteById(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
