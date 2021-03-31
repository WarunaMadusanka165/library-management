package bo.Impl;

import bo.BOFactory;
import bo.custom.BookBO;
import bo.custom.BorrowBO;
import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.BookDAO;
import dao.custom.BorrowDAO;
import db.DBConnection;
import dto.BorrowDTO;
import entity.Borrow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowBOImpl implements BorrowBO {
    BookBO bookBO= (BookBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOK);
    BorrowDAO borrowDAO = (BorrowDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BORROW);
    @Override
    public boolean addBorrow(BorrowDTO borrow) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        boolean add = borrowDAO.add(borrow);
        if (add){
            boolean b = bookBO.updateBookQTY(borrow.getIsbn(),1);

            if (!b){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        }
        connection.rollback();
        connection.setAutoCommit(true);
        return false;
    }

    @Override
    public boolean updateBorrow(BorrowDTO borrow) throws SQLException, ClassNotFoundException {
        return borrowDAO.update(borrow);
    }

    @Override
    public boolean deleteBorrow(String memberID) throws SQLException, ClassNotFoundException {
        return borrowDAO.delete(memberID);
    }

    @Override
    public BorrowDTO searchBorrow(String memberID) throws SQLException, ClassNotFoundException {
        return borrowDAO.search(memberID);
    }

    @Override
    public List<BorrowDTO> loadAllBorrow() throws ClassNotFoundException, SQLException {
        List<BorrowDTO> borrowList = borrowDAO.getAll();
        List<BorrowDTO> borrowDTOList = new ArrayList<>();
        for (BorrowDTO list : borrowList){
            borrowDTOList.add(
                    new BorrowDTO(
                            list.getIsbn(),
                            list.getMemberID(),
                            list.getFine(),
                            list.getDue_date(),
                            list.getStates(),
                            list.getIssued_date(),
                            list.getReturn_date()
                    )
            );
        }
        return borrowDTOList;
    }

    @Override
    public boolean returnBook(BorrowDTO borrowDTO) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        boolean returnBook = borrowDAO.returnBook(borrowDTO);
//        if (returnBook){
//            boolean book = bookBO.updateReturnBookQTY(borrowDTO.getIsbn(), 1);
//            if (!book){
//                connection.rollback();
//                connection.setAutoCommit(true);
//                return false;
//            }
//            connection.commit();
//            connection.setAutoCommit(true);
//            return true;
//        }
//        connection.rollback();
//        connection.setAutoCommit(true);
//        return false;
        System.out.println("borrowDTO = " + borrowDTO.getIsbn());
        boolean b1 = false;
        if (returnBook){
            b1 =bookBO.updateReturnBookQTY(borrowDTO.getIsbn(),1);
        }
        return b1;
        //return borrowDAO.returnBook(borrowDTO);
    }

    @Override
    public List<BorrowDTO> memberBookCount(BorrowDTO borrowDTO) throws ClassNotFoundException, SQLException {
        List<BorrowDTO> borrowDTOList = borrowDAO.memberBookCount(borrowDTO);
        ArrayList<BorrowDTO> borrowDTOArrayList  = new ArrayList<>();
        for (BorrowDTO list:borrowDTOList) {
            borrowDTOArrayList.add(
                    new BorrowDTO(
                            list.getMemberID(),
                            list.getStates()
                    )
            );
        }
        return borrowDTOArrayList;
    }
}