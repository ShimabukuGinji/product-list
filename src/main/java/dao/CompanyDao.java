package dao;

import record.CompanyRecord;
import record.UserRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao {
    private Connection connection;

    public CompanyDao(Connection connection) {
        this.connection = connection;
    }

    public List<CompanyRecord> nameAll() {
        final var SQL = "SELECT * FROM companys;";

        var list = new ArrayList<CompanyRecord>();

        try (var statement = this.connection.prepareStatement(SQL)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var company = new CompanyRecord(resultSet.getInt("id"), resultSet.getString("name"));
                list.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return list;
    }

    public int insert(UserRecord product) throws RuntimeException{
        final var SQL = "INSERT INTO products VALUES(?,?,?)";

        try (var statement = this.connection.prepareStatement(SQL)) {
            statement.setInt(1, product.id());
            statement.setString(2, product.name());
            statement.setInt(3, product.score());
            var result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public int update(UserRecord product) {
        final var SQL = "UPDATE products SET name = ?,price = ? WHERE id = ?";
        try (var statement = this.connection.prepareStatement(SQL)) {
            statement.setString(1, product.name());
            statement.setInt(2, product.score());
            statement.setInt(3, product.id());
            var result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public int delete(int id) {
        final var SQL = "DELETE FROM products WHERE id = ?";
        try (var statement = this.connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            var result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
