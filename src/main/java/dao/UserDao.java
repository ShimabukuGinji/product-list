package dao;

import record.TableRecord;
import record.UserRecord;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public List<TableRecord> nameAll() {
        final var SQL = "SELECT users.id id, companys.name company, users.name name, score FROM users JOIN companys ON users.company_id = companys.id ORDER BY users.id;";

        var list = new ArrayList<TableRecord>();

        try (var statement = this.connection.prepareStatement(SQL)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var user = new TableRecord(resultSet.getInt("id"), resultSet.getString("company"), resultSet.getString("name"), resultSet.getInt("score"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return list;
    }

    public UserRecord findById(int id) {
        final var SQL = "SELECT id,company_id,name,score FROM users WHERE id = ?";
        try (var statement = this.connection.prepareStatement(SQL);) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var user = new UserRecord(resultSet.getInt("id"), resultSet.getInt("company_id"), resultSet.getString("name"), resultSet.getInt("score"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return null;
    }

    public List<UserRecord> findByName(String name) {
        final var SQL = "SELECT * FROM products WHERE name LIKE ?";
        var list = new ArrayList<UserRecord>();
        try (var statement = this.connection.prepareStatement(SQL)) {
            statement.setString(1, "%" + name + "%");
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var product = new UserRecord(resultSet.getInt("id"), resultSet.getInt("company_id"), resultSet.getString("name"), resultSet.getInt("score"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return list;
    }

    public int insert(UserRecord user) throws RuntimeException{
        final var SQL = "INSERT INTO users (company_id,name,score) VALUES(?,?,?)";
        try (var statement = this.connection.prepareStatement(SQL)) {
            statement.setInt(1, user.company_id());
            statement.setString(2, user.name());
            statement.setInt(3, user.score());
            var result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public int update(UserRecord user) {
        final var SQL = "UPDATE users SET company_id = ?, name = ?, score = ? WHERE id = ?";
        try (var statement = this.connection.prepareStatement(SQL)) {
            statement.setInt(1, user.company_id());
            statement.setString(2, user.name());
            statement.setInt(3, user.score());
            statement.setInt(4, user.id());
            var result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public int delete(int id) {
        final var SQL = "DELETE FROM users WHERE id = ?";
        try (var statement = this.connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            var result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public int UserIdSequence(){
        final var SQL = "SELECT last_value FROM users_id_seq;";
        try (var statement = this.connection.prepareStatement(SQL)){
            var result = statement.executeQuery();
            while (result.next()) {
                return result.getInt("last_value");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
