package dao;

import dao.custom.impl.BookDAOImpl;
import dao.custom.impl.BorrowDAOImpl;
import dao.custom.impl.MemberDAOImpl;
import dao.custom.impl.PublisherDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance(){
        if (daoFactory==null){
            daoFactory=new DAOFactory();
        }
            return daoFactory;
    }
    public enum DAOTypes{
        MEMBER,PUBLISHER,BOOK,BORROW
    }

    public CrudDAO<? extends Object, String> getDAO(DAOTypes types){
        switch (types){
            case MEMBER:
                return new MemberDAOImpl();
            case PUBLISHER:
                return new PublisherDAOImpl();
            case BOOK:
                return new BookDAOImpl();
            case BORROW:
                return new BorrowDAOImpl();
            default:
                return null;
        }
    }
}
