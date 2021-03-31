package bo.Impl;

import bo.custom.MemberBO;
import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.MemberDAO;
import dto.MemberDTO;
import entity.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberBOImpl implements MemberBO {
    MemberDAO memberDAO = (MemberDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MEMBER);

    @Override
    public boolean addMember(MemberDTO dto) throws SQLException, ClassNotFoundException {
        MemberDTO member = new MemberDTO(dto.getMemberID(), dto.getName(), dto.getAddress(), dto.getContact_number());
        return memberDAO.add(member);
    }

    @Override
    public boolean updateMember(MemberDTO member) throws SQLException, ClassNotFoundException {
       // Member member = new Member(dto.getMemberID(), dto.getName(), dto.getAddress(), dto.getContact_number());
        return memberDAO.update(member);
    }

    @Override
    public boolean deleteMember(String memberID) throws SQLException, ClassNotFoundException {
        return memberDAO.delete(memberID);
    }

    @Override
    public MemberDTO searchMember(String memberID) throws SQLException, ClassNotFoundException {
        return memberDAO.search(memberID);
    }

    @Override
    public List<MemberDTO> loadAllMembers() throws ClassNotFoundException, SQLException {
        List<MemberDTO> memberList = memberDAO.getAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberDTO list : memberList){
            memberDTOList.add(
                    new MemberDTO(
                            list.getMemberID(),
                            list.getName(),
                            list.getAddress(),
                            list.getContact_number()
                    )
            );
        }
        return memberDTOList;
    }


}
