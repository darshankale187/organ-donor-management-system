import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipientDAO {

    public int addRecipient(Recipient recipient) throws SQLException {
        String sql = "INSERT INTO recipients (name, age, blood_group, organ_required, urgency_level, contact) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, recipient.getName());
            ps.setInt(2, recipient.getAge());
            ps.setString(3, recipient.getBloodGroup());
            ps.setString(4, recipient.getOrganRequired());
            ps.setString(5, recipient.getUrgencyLevel());
            ps.setString(6, recipient.getContact());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }

    public List<Recipient> getAllRecipients() throws SQLException {
        List<Recipient> list = new ArrayList<>();
        // CRITICAL/HIGH urgency patients listed first - shows ORDER BY with CASE
        String sql = "SELECT * FROM recipients ORDER BY " +
                "CASE urgency_level " +
                "WHEN 'CRITICAL' THEN 1 " +
                "WHEN 'HIGH' THEN 2 " +
                "WHEN 'MEDIUM' THEN 3 " +
                "ELSE 4 END, recipient_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Recipient getRecipientById(int recipientId) throws SQLException {
        String sql = "SELECT * FROM recipients WHERE recipient_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, recipientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        }
    }

    public boolean deleteRecipient(int recipientId) throws SQLException {
        String sql = "DELETE FROM recipients WHERE recipient_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, recipientId);
            return ps.executeUpdate() > 0;
        }
    }

    private Recipient mapRow(ResultSet rs) throws SQLException {
        return new Recipient(
                rs.getInt("recipient_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("blood_group"),
                rs.getString("organ_required"),
                rs.getString("urgency_level"),
                rs.getString("contact")
        );
    }
}