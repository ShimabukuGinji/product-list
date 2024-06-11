package service;

import com.example.dbusersmanagement.UserNotFoundException;
import dao.UserDao;
import record.TableRecord;
import record.UserRecord;
import util.DBUtil;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    public List<TableRecord> nameAll() {
        try(var connection = DBUtil.getConnection();) {
            var UserDao = new UserDao(connection);
            return UserDao.nameAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserRecord findById(int id) {
        try(var connection = DBUtil.getConnection();) {
            var ProductDao = new UserDao(connection);
            var findId = ProductDao.findById(id);
            if (findId != null) {
                return findId;
            }
            throw new UserNotFoundException();
        } catch (SQLException e) {
            return null;
        }
    }

//    public List<UserRecord> findByName(String name) {
//        try(var connection = DBUtil.getConnection();) {
//            var ProductDao = new UserDao(connection);
//            return ProductDao.findByName(name);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public int insert(UserRecord user){
        try(var connection = DBUtil.getConnection();) {
            var ProductDao = new UserDao(connection);
            return ProductDao.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int UserIdSequence(){
        try(var connection = DBUtil.getConnection();) {
            var UserDao = new UserDao(connection);
            return UserDao.UserIdSequence();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(UserRecord user){
        try(var connection = DBUtil.getConnection();){
            var ProductDao = new UserDao(connection);
            return ProductDao.update(user);
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(int id){
        try(var connection = DBUtil.getConnection();){
            var ProductDao = new UserDao(connection);
            return ProductDao.delete(id);
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
