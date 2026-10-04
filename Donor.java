public class Donor {
    private int donorId;
    private String name;
    private int age;
    private String bloodGroup;
    private String organ;
    private String contact;
    private boolean available;

    public Donor() {}

    public Donor(int donorId, String name, int age, String bloodGroup,
                 String organ, String contact, boolean available) {
        this.donorId = donorId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.organ = organ;
        this.contact = contact;
        this.available = available;
    }

    // Constructor for creating a new donor (no ID yet)
    public Donor(String name, int age, String bloodGroup, String organ, String contact) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.organ = organ;
        this.contact = contact;
        this.available = true;
    }

    public int getDonorId() { return donorId; }
    public void setDonorId(int donorId) { this.donorId = donorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getOrgan() { return organ; }
    public void setOrgan(String organ) { this.organ = organ; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-4d %-6s %-10s %-15s %s",
                donorId, name, age, bloodGroup, organ, contact,
                available ? "AVAILABLE" : "NOT AVAILABLE");
    }
}