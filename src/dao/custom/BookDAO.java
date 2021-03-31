package dao.custom;

import dao.CrudDAO;
import dto.BookDTO;
import entity.Book;

import java.sql.SQLException;

public interface BookDAO extends CrudDAO<BookDTO,String> {
    boolean updateBookQTY(int QTY,String bookID)throws ClassNotFoundException, SQLException;
    boolean updateReturnBookQty(int QTY,String bookID)throws ClassNotFoundException,SQLException;
}
