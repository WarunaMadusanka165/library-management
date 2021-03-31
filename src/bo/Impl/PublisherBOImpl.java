package bo.Impl;

import bo.custom.PublisherBO;
import dao.DAOFactory;
import dao.custom.PublisherDAO;
import dto.PublisherDTO;
import entity.Publisher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherBOImpl implements PublisherBO {

    PublisherDAO publisherDAO = (PublisherDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PUBLISHER);

    @Override
    public boolean addPublisher(PublisherDTO publisher) throws SQLException, ClassNotFoundException {
        return publisherDAO.add(publisher);
    }

    @Override
    public boolean updatePublisher(PublisherDTO publisher) throws SQLException, ClassNotFoundException {
        return publisherDAO.update(publisher);
    }

    @Override
    public boolean deletePublisher(String publisherID) throws SQLException, ClassNotFoundException {
        return publisherDAO.delete(publisherID);
    }

    @Override
    public PublisherDTO searchPublisher(String publisherID) throws SQLException, ClassNotFoundException {
        return publisherDAO.search(publisherID);
    }

    @Override
    public List<PublisherDTO> loadAllPublisher() throws ClassNotFoundException, SQLException {
        List<PublisherDTO> memberList = publisherDAO.getAll();
        List<PublisherDTO> publisherDTOList = new ArrayList<>();
        for (PublisherDTO list : memberList){
            publisherDTOList.add(
                    new PublisherDTO(
                            list.getPublishID(),
                            list.getName(),
                            list.getAddress()
                    )
            );
        }
        return publisherDTOList;
    }

}
