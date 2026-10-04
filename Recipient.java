public class Recipient {
    private int recipientId;
    private String name;
    private int age;
    private String bloodGroup;
    private String organRequired;
    private String urgencyLevel; // LOW, MEDIUM, HIGH, CRITICAL
    private String contact;

    public Recipient() {}

    public Recipient(int recipientId, String name, int age, String bloodGroup,
                      String organRequired, String urgencyLevel, String contact) {
        this.recipientId = recipientId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.organRequired = organRequired;
        this.urgencyLevel = urgencyLevel;
        this.contact = contact;
    }

    // Constructor for creating a new recipient (no ID yet)
    public Recipient(String name, int age, String bloodGroup, String organRequired,
                      String urgencyLevel, String contact) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.organRequired = organRequired;
        this.urgencyLevel = urgencyLevel;
        this.contact = contact;
    }

    public int getRecipientId() { return recipientId; }
    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getOrganRequired() { return organRequired; }
    public void setOrganRequired(String organRequired) { this.organRequired = organRequired; }

    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-4d %-6s %-10s %-10s %s",
                recipientId, name, age, bloodGroup, organRequired, urgencyLevel, contact);
    }
}