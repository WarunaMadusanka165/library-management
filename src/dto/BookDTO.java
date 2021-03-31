package dto;

public class BookDTO {
    private String ISBN;
    private String pubID;
    private String title;
    private String author;
    private int qty;
    private double price;

    public BookDTO() {
    }

    public BookDTO(String ISBN, String pubID, String title, String author, int qty, double price) {
        this.ISBN = ISBN;
        this.pubID = pubID;
        this.title = title;
        this.author = author;
        this.qty = qty;
        this.price = price;
    }

    public BookDTO(String ISBN, int qty) {
        this.ISBN = ISBN;
        this.qty = qty;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPubID() {
        return pubID;
    }

    public void setPubID(String pubID) {
        this.pubID = pubID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}
