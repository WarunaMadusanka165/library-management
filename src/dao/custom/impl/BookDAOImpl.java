package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.BookDAO;
import dto.BookDTO;
import entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public boolean add(BookDTO entity) throws SQLException, ClassNotFoundException {
        String sql = "insert into Book values (?,?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,entity.getISBN(),entity.getPubID(),entity.getTitle(),entity.getAuthor(),entity.getQty(),entity.getPrice());
    }

    @Override
    public boolean update(BookDTO entity) throws SQLException, ClassNotFoundException {
        String sql = "update Book set publishID=?,title=?,author=?,qty=?,price=? where isbn=?";
        return CrudUtil.executeUpdate(sql,entity.getPubID(),entity.getTitle(),entity.getAuthor(),entity.getQty(),entity.getPrice(),entity.getISBN());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql= "delete from Book where isbn=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    @Override
    public BookDTO search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from Book where isbn=?";
        ResultSet rst = CrudUtil.executeQuery(sql, id);
        if (rst.next()){
            String isbn = rst.getString(1);
            String pubID = rst.getString(2);
            String title = rst.getString(3);
            String author = rst.getString(4);
            int qty = rst.getInt(5);
            double price = rst.getDouble(6);

            return new BookDTO(isbn,pubID,title,author,qty,price);
        }
        return null;
    }

    @Override
    public List<BookDTO> getAll() throws SQLException, ClassNotFoundException {
        String sql = "select * from Book";
        ResultSet rst = CrudUtil.executeQuery(sql);
        ArrayList<BookDTO> book = new ArrayList<>();
        while (rst.next()){
            book.add(new BookDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            ));
        }
        return book;
    }

    @Override
    public boolean updateBookQTY(int QTY,String bookID) throws ClassNotFoundException, SQLException {
        BookDTO dto = search(bookID);
        int qty = dto.getQty();
        qty=qty-QTY;
        return CrudUtil.executeUpdate("update book set qty=? where isbn=?",qty,bookID);
    }
    private int qty;
    @Override
    public boolean updateReturnBookQty(int QTY, String bookID) throws ClassNotFoundException, SQLException {
        BookDTO bookDTO = search(bookID);
        System.out.println("QTY = " + QTY);
         qty = bookDTO.getQty();
        qty=qty+QTY;
        System.out.println("qty = " + qty);
        System.out.println("QTY = " + QTY);
        return CrudUtil.executeUpdate("update book set qty=? where isbn=?",qty,bookID);
    }
}
