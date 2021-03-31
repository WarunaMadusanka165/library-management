package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.MemberDAO;
import dao.custom.PublisherDAO;
import db.DBConnection;
import dto.PublisherDTO;
import entity.Member;
import entity.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAOImpl implements PublisherDAO {
    @Override
    public boolean add(PublisherDTO entity) throws SQLException, ClassNotFoundException {
        String sql = "Insert into Publisher values (?,?,?)";
        return CrudUtil.executeUpdate(sql,entity.getPublishID(),entity.getName(),entity.getAddress());
    }

    @Override
    public boolean update(PublisherDTO entity) throws SQLException, ClassNotFoundException {
        String sql = "update Publisher set name=?,address=? where publishID=?";
        return CrudUtil.executeUpdate(sql,entity.getName(),entity.getAddress(),entity.getPublishID());
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from publisher where publishID=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    @Override
    public PublisherDTO search(String id) throws SQLException, ClassNotFoundException {
        String sql = "select * from Publisher where publishID=?";
        ResultSet rst = CrudUtil.executeQuery(sql, id);
        if (rst.next()){
            String pubId = rst.getString(1);
            String pubName = rst.getString(2);
            String pubAddress = rst.getString(3);

            return new PublisherDTO(pubId,pubName,pubAddress);
        }
        return null;
    }

    @Override
    public List<PublisherDTO> getAll() throws SQLException, ClassNotFoundException {
        String sql = "select * from publisher";
        ResultSet rst = CrudUtil.executeQuery(sql);
        ArrayList<PublisherDTO> publisher = new ArrayList<>();
        while (rst.next()){
            publisher.add(new PublisherDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return publisher;
    }

}
