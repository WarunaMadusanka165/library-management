package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.MemberDAO;
import db.DBConnection;
import dto.MemberDTO;
import entity.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public boolean add(MemberDTO entity) throws SQLException, ClassNotFoundException {
       /* Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("insert into member values (?,?,?,?)");
        pstm.setObject(1, entity.getMemberID());
        pstm.setObject(2, entity.getName());
        pstm.setObject(3, entity.getAddress());
        pstm.setObject(4, entity.getContact_number());*/

        String sql = "insert into member values (?,?,?,?)";
        return CrudUtil.executeUpdate(sql,entity.getMemberID(),entity.getName(),entity.getAddress(),entity.getContact_number());

        //return pstm.executeUpdate() > 0;

    }

    @Override
    public boolean update(MemberDTO entity) throws SQLException, ClassNotFoundException {
        String sql = "update Member set name=?,address=?,contact_number=? where memberID=?";
       return CrudUtil.executeUpdate(sql,entity.getName(),entity.getAddress(),entity.getContact_number(),entity.getMemberID());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        String sql= "delete from member where memberID=?";
        return CrudUtil.executeUpdate(sql,id);
    }

    @Override
    public MemberDTO search(String id) throws SQLException, ClassNotFoundException {
       /* Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("select * from member where memberID=?");
        pstm.setObject(1, id);
        ResultSet rst = pstm.executeQuery();*/

       String sql = "select * from member where memberID=?";
        ResultSet rst = CrudUtil.executeQuery(sql, id);
        if (rst.next()){
            String memberId = rst.getString(1);
            String memberName = rst.getString(2);
            String memberAddress = rst.getString(3);
            int memberContact = Integer.parseInt(rst.getString(4));

            return new MemberDTO(memberId,memberName,memberAddress,memberContact);
        }
        return null;
    }

    @Override
    public List<MemberDTO> getAll() throws SQLException, ClassNotFoundException {
        String sql = "select * from member";
        ResultSet rst = CrudUtil.executeQuery(sql);
        ArrayList<MemberDTO> member = new ArrayList<>();
        while (rst.next()){
            member.add(new MemberDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4)
            ));
        }
        return member;
    }




    /*@Override
    public boolean addMember(MemberDTO member) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("insert into member values (?,?,?,?)");
        pstm.setObject(1, member.getMemberID());
        pstm.setObject(2, member.getName());
        pstm.setObject(3, member.getAddress());
        pstm.setObject(4, member.getContact_number());

        return pstm.executeUpdate() > 0;

    }
    @Override
    public boolean updateMember(MemberDTO member) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("update member set name=?,address=?,contact_number=? where memberID=?");
        pstm.setObject(1, member.getName());
        pstm.setObject(2, member.getAddress());
        pstm.setObject(3, member.getContact_number());
        pstm.setObject(4, member.getMemberID());

        return pstm.executeUpdate() > 0;
    }
    @Override
    public boolean deleteMember(String memberID) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("delete from member where memberID=?");

        pstm.setObject(1, memberID);

        return pstm.executeUpdate() > 0;
    }
    @Override
    public MemberDTO searchMember(String memberID) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("select * from member where memberID=?");

        pstm.setObject(1, memberID);

        ResultSet rst = pstm.executeQuery();
        if (rst.next()){
            String memberId = rst.getString(1);
            String memberName = rst.getString(2);
            String memberAddress = rst.getString(3);
            int memberContact = Integer.parseInt(rst.getString(4));

            return new MemberDTO(memberId,memberName,memberAddress,memberContact);
        }
        return null;
    }
    /*public ArrayList<MemberDTO> getAllMembers(){

    }*/

}
