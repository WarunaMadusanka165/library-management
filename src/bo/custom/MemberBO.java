package bo.custom;

import bo.SuperBO;
import dto.MemberDTO;
import entity.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberBO extends SuperBO {
    boolean addMember(MemberDTO member) throws SQLException, ClassNotFoundException;
    boolean updateMember(MemberDTO member) throws SQLException, ClassNotFoundException;
    boolean deleteMember(String memberID) throws SQLException, ClassNotFoundException;
    MemberDTO searchMember(String memberID) throws SQLException, ClassNotFoundException;
    List<MemberDTO> loadAllMembers() throws ClassNotFoundException, SQLException;
}
