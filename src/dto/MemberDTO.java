package dto;

public class MemberDTO {
    private String memberID;
    private String name;
    private String address;
    private int contact_number;

    public MemberDTO(String memberID, String name, int contact_number, String address) {
    }

    public MemberDTO(String memberID, String name, String address, int contact_number) {
        this.memberID = memberID;
        this.name = name;
        this.address = address;
        this.contact_number = contact_number;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
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

    public int getContact_number() {
        return contact_number;
    }

    public void setContact_number(int contact_number) {
        this.contact_number = contact_number;
    }
}
