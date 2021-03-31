package bo;

import bo.Impl.BookBOImpl;
import bo.Impl.BorrowBOImpl;
import bo.Impl.MemberBOImpl;
import bo.Impl.PublisherBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance(){
        if (boFactory==null){
            boFactory=new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes{
        PUBLISHER,MEMBER,BOOK,BORROW
    }
    public SuperBO getBO(BOTypes types){
        switch (types){
            case PUBLISHER:
                return new PublisherBOImpl();
            case MEMBER:
                return new MemberBOImpl();
            case BOOK:
                return new BookBOImpl();
            case BORROW:
                return new BorrowBOImpl();
            default:
                return null;
        }
    }
}
