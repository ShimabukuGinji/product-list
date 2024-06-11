package service;

import com.example.dbusersmanagement.UserNotFoundException;
import dao.CompanyDao;
import dao.UserDao;
import record.CompanyRecord;
import record.UserRecord;
import util.DBUtil;

import java.sql.SQLException;
import java.util.List;

public class CompanyService {

    public List<CompanyRecord> nameAll() {
        try(var connection = DBUtil.getConnection();) {
            var CompanyDao = new CompanyDao(connection);
            return CompanyDao.nameAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //企業編集用
//    public int insert(UserRecord product){
//        try(var connection = DBUtil.getConnection();) {
//            var ProductDao = new UserDao(connection);
//            return ProductDao.insert(product);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    public int update(UserRecord product){
//        try(var connection = DBUtil.getConnection();){
//            var ProductDao = new UserDao(connection);
//            return ProductDao.update(product);
//        } catch (SQLException e){
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    public int delete(int id){
//        try(var connection = DBUtil.getConnection();){
//            var ProductDao = new UserDao(connection);
//            return ProductDao.delete(id);
//        } catch (SQLException e){
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    public int bulkInsert(List<UserRecord> products){
//        try(var connection = DBUtil.getConnection();){
//            connection.setAutoCommit(false);
//            var ProductDao = new UserDao(connection);
//            for (var product : products){
//                ProductDao.insert(product);
//            }
//            connection.commit();
//            return products.size();
//        } catch (SQLException | RuntimeException e){
//            return 0;
//        }
//    }
}
