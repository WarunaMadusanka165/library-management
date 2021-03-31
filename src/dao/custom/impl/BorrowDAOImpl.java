package dao.custom.impl;


import dao.CrudUtil;
import dao.custom.BorrowDAO;
import dto.BorrowDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAOImpl implements BorrowDAO {

    @Override
    public boolean add(BorrowDTO entity) throws SQLException, ClassNotFoundException {
        String sql = "insert into Borrow (isbn,memberID,due_date,states,issued_date)values(?,?,?,?,?)";
        return CrudUtil.executeUpdate(sql,entity.getIsbn(),entity.getMemberID(),entity.getDue_date(),entity.getStates(),entity.getIssued_date());
    }

    @Override
    public boolean update(BorrowDTO entity) throws SQLException, ClassNotFoundException {
        String sql = "update Borrow set isbn=?,fine=?,due_date=?,states=?,issued_date=?,return_date=? where memberID=?";
        return CrudUtil.executeUpdate(sql,entity.getIsbn(),entity.getFine(),entity.getDue_date(),entity.getStates(),entity.getIssued_date(),entity.getReturn_date(),entity.getMemberID());
    }

    @Override
    public boolean delete(String memberID) throws SQLException, ClassNotFoundException {
        String sql= "delete from Borrow where memberID=?";
        return CrudUtil.executeUpdate(sql,memberID);
    }

    @Override
    public BorrowDTO search(String memberId) throws SQLException, ClassNotFoundException {
        String sql = "select * from Borrow where memberID=?";
        ResultSet rst = CrudUtil.executeQuery(sql, memberId);
        if (rst.next()){
            String isbn = rst.getString(1);
            String memberID = rst.getString(2);
            double fine = rst.getDouble(3);
            String due_date = rst.getString(4);
            String states = rst.getString(5);
            String issued_date = rst.getString(6);
            String return_date = rst.getString(7);


            return new BorrowDTO(isbn,memberID,fine,due_date,states,issued_date,return_date);
        }
        return null;
    }

    @Override
    public List<BorrowDTO> getAll() throws SQLException, ClassNotFoundException {
        String sql = "select * from Borrow";
        ResultSet rst = CrudUtil.executeQuery(sql);
        ArrayList<BorrowDTO> borrow = new ArrayList<>();
        while (rst.next()){
            borrow.add(new BorrowDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return borrow;
    }

    @Override
    public boolean returnBook(BorrowDTO entity) throws ClassNotFoundException, SQLException {
        String sql = "update borrow set fine=?,states=?,return_date=? where memberID=?";
        return CrudUtil.executeUpdate(sql,entity.getFine(),entity.getStates(),entity.getReturn_date(),entity.getMemberID());
    }

    @Override
    public List<BorrowDTO> memberBookCount(BorrowDTO entity) throws ClassNotFoundException, SQLException {
        String sql="select memberID,states from borrow where states='give'";
        ResultSet resultSet = CrudUtil.executeQuery(sql, entity);
        ArrayList<BorrowDTO> borrowDTO = new ArrayList<>();
        while (resultSet.next()){
            borrowDTO.add(new BorrowDTO(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return borrowDTO;
    }
}

