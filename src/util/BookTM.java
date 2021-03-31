package util;

public class BookTM {
    private String isbn;
    private String publishID;
    private String title;
    private String author;
    private int qty;
    private double price;

    public BookTM() {
    }

    public BookTM(String isbn, String publishID, String title, String author, int qty, double price) {
        this.isbn = isbn;
        this.publishID = publishID;
        this.title = title;
        this.author = author;
        this.qty = qty;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishID() {
        return publishID;
    }

    public void setPublishID(String publishID) {
        this.publishID = publishID;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
