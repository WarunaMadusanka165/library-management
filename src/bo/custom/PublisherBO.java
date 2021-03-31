package bo.custom;

import bo.SuperBO;
import dto.PublisherDTO;
import entity.Publisher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PublisherBO extends SuperBO {
     boolean addPublisher(PublisherDTO publisher) throws SQLException, ClassNotFoundException;
     boolean updatePublisher(PublisherDTO publisher) throws SQLException, ClassNotFoundException;
     boolean deletePublisher(String publisherID) throws SQLException, ClassNotFoundException;
     PublisherDTO searchPublisher(String publisherID) throws SQLException, ClassNotFoundException;
     List<PublisherDTO> loadAllPublisher() throws ClassNotFoundException, SQLException;
}

