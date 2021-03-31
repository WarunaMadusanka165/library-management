package dto;

public class PublisherDTO {
    private String publishID;
    private String name;
    private String address;

    public PublisherDTO() {
    }

    public PublisherDTO(String publishID, String name, String address) {
        this.publishID = publishID;
        this.name = name;
        this.address = address;
    }

    public String getPublishID() {
        return publishID;
    }

    public void setPublishID(String publishID) {
        this.publishID = publishID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
