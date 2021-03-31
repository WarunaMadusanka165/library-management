package bo.custom;

import bo.SuperBO;
import dto.BorrowDTO;
import entity.Borrow;

import java.sql.SQLException;
import java.util.List;

public interface BorrowBO extends SuperBO {
    boolean addBorrow(BorrowDTO borrow) throws SQLException, ClassNotFoundException;
    boolean updateBorrow(BorrowDTO borrow) throws SQLException, ClassNotFoundException;
    boolean deleteBorrow(String memberID) throws SQLException, ClassNotFoundException;
    BorrowDTO searchBorrow(String memberID) throws SQLException, ClassNotFoundException;
    List<BorrowDTO> loadAllBorrow() throws ClassNotFoundException, SQLException;
    boolean returnBook(BorrowDTO borrowDTO) throws SQLException,ClassNotFoundException;
    List<BorrowDTO> memberBookCount(BorrowDTO borrowDTO) throws ClassNotFoundException,SQLException;
}
