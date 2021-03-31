package bo.Impl;

import bo.custom.BookBO;
import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.BookDAO;
import dto.BookDTO;
import entity.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {

    BookDAO dao = (BookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOK);

    @Override
    public boolean addBook(BookDTO book) throws SQLException, ClassNotFoundException {
        return dao.add(book);
    }

    @Override
    public boolean updateBook(BookDTO book) throws SQLException, ClassNotFoundException {
        return dao.update(book);
    }

    @Override
    public boolean deleteBook(String isbn) throws SQLException, ClassNotFoundException {
        return dao.delete(isbn);
    }

    @Override
    public BookDTO searchBook(String isbn) throws SQLException, ClassNotFoundException {
        return dao.search(isbn);
    }

    @Override
    public List<BookDTO> loadAllBook() throws ClassNotFoundException, SQLException {
        List<BookDTO> bookList = dao.getAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookDTO list : bookList){
            bookDTOList.add(
                    new BookDTO(
                            list.getISBN(),
                            list.getPubID(),
                            list.getTitle(),
                            list.getAuthor(),
                            list.getQty(),
                            list.getPrice()
                    )
            );
        }
        return bookDTOList;
    }

    @Override
    public boolean updateBookQTY(String bookID ,int qty) throws ClassNotFoundException, SQLException {
        return dao.updateBookQTY(qty,bookID);
    }

    @Override
    public boolean updateReturnBookQTY(String bookID, int qty) throws ClassNotFoundException, SQLException {
        return dao.updateReturnBookQty(qty,bookID);
    }
}
