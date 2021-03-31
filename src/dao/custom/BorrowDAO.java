package dao.custom;

import dao.CrudDAO;
import dto.BorrowDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BorrowDAO extends CrudDAO<BorrowDTO,String> {
    boolean returnBook(BorrowDTO entity) throws ClassNotFoundException, SQLException;
    List<BorrowDTO> memberBookCount(BorrowDTO entity) throws ClassNotFoundException,SQLException;
}
