package bo.custom;

import bo.SuperBO;
import dto.BookDTO;
import entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookBO extends SuperBO {
    boolean addBook(BookDTO book) throws SQLException, ClassNotFoundException;
    boolean updateBook(BookDTO book) throws SQLException, ClassNotFoundException;
    boolean deleteBook(String isbn) throws SQLException, ClassNotFoundException;
    BookDTO searchBook(String isbn) throws SQLException, ClassNotFoundException;
    List<BookDTO> loadAllBook() throws ClassNotFoundException, SQLException;
    boolean updateBookQTY(String bookID ,int qty)throws  ClassNotFoundException,SQLException;
    boolean updateReturnBookQTY(String bookID ,int qty) throws ClassNotFoundException,SQLException;
    //change

}
