package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.dao.interfaces.ITransactionDAO;
import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Transaction;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO implements ITransactionDAO {
//    private final Connection connection = ConnectionManager.getConnection();

    private static final String FIND_USER_TRANSACTIONS_SQL = "SELECT * FROM Transactions WHERE user_id = ? AND group_id IS NULL";
    private static final String FIND_USER_TRANSACTIONS_DAY_SQL = "SELECT * FROM Transactions WHERE user_id = ? AND group_id IS NULL AND DATE(date_time) = CURRENT_DATE";
    private static final String FIND_USER_TRANSACTIONS_MONTH_SQL = "SELECT * FROM Transactions WHERE user_id = ? AND group_id IS NULL AND date_time >= CURRENT_DATE - INTERVAL '30 days'";
    private static final String FIND_USER_TRANSACTIONS_YEAR_SQL = "SELECT * FROM Transactions WHERE user_id = ? AND group_id IS NULL AND date_time >= CURRENT_DATE - INTERVAL '365 days'";
    private static final String FIND_GROUP_TRANSACTIONS_SQL = "SELECT * FROM Transactions WHERE group_id = ?";
    private static final String FIND_GROUP_TRANSACTIONS_DAY_SQL = "SELECT * FROM Transactions WHERE group_id = ? AND DATE(date_time) = CURRENT_DATE";
    private static final String FIND_GROUP_TRANSACTIONS_MONTH_SQL = "SELECT * FROM Transactions WHERE group_id = ? AND date_time >= CURRENT_DATE - INTERVAL '30 days'";
    private static final String FIND_GROUP_TRANSACTIONS_YEAR_SQL = "SELECT * FROM Transactions WHERE group_id = ? AND date_time >= CURRENT_DATE - INTERVAL '365 days'";
    private static final String FIND_TRANSACTION_BY_ID_SQL = "SELECT * FROM Transactions WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Transactions (user_id, group_id, amount, category, type, date_time, description) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM Transactions WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE Transactions SET amount = ?, category = ?, type = ?, description = ? WHERE id = ?";

    @Override
    public List<Transaction> findUserTransactions(int userId, String period) {
        List<Transaction> transactions = new ArrayList<>();
        String sql;
        switch (period) {
            case "day":
                sql = FIND_USER_TRANSACTIONS_DAY_SQL;
                break;
            case "month":
                sql = FIND_USER_TRANSACTIONS_MONTH_SQL;
                break;
            case "year":
                sql = FIND_USER_TRANSACTIONS_YEAR_SQL;
                break;
            default:
                sql = FIND_USER_TRANSACTIONS_SQL;
        }

        try (Connection connection = ConnectionManager.getConnectionNonSingleton();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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

    @Override
    public List<Transaction> findGroupTransactions(int groupId, String period) {
        List<Transaction> transactions = new ArrayList<>();
        String sql;
        switch (period) {
            case "day":
                sql = FIND_GROUP_TRANSACTIONS_DAY_SQL;
                break;
            case "month":
                sql = FIND_GROUP_TRANSACTIONS_MONTH_SQL;
                break;
            case "year":
                sql = FIND_GROUP_TRANSACTIONS_YEAR_SQL;
                break;
            default:
                sql = FIND_GROUP_TRANSACTIONS_SQL;
        }

        try (Connection connection = ConnectionManager.getConnectionNonSingleton();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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

    @Override
    public Transaction findTransactionById(int transactionId) {
        try (Connection connection = ConnectionManager.getConnectionNonSingleton();
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

    @Override
    public Transaction save(Transaction transaction) {
        try (Connection connection = ConnectionManager.getConnectionNonSingleton();
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

    @Override
    public void update(Transaction updatedTransaction) {
        try (Connection connection = ConnectionManager.getConnectionNonSingleton();
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

    @Override
    public void deleteById(int id) {
        try (Connection connection = ConnectionManager.getConnectionNonSingleton();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
