package util;

public class BorrowTM {
    private String isbn;
    private String memberID;
    private double fine;
    private String due_date;
    private String states;
    private String issued_date;
    private String return_date;

    public BorrowTM() {
    }

    public BorrowTM(String isbn, String memberID, double fine, String due_date, String states, String issued_date, String return_date) {
        this.isbn = isbn;
        this.memberID = memberID;
        this.fine = fine;
        this.due_date = due_date;
        this.states = states;
        this.issued_date = issued_date;
        this.return_date = return_date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getIssued_date() {
        return issued_date;
    }

    public void setIssued_date(String issued_date) {
        this.issued_date = issued_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }
}
