import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {

    public int addDonor(Donor donor) throws SQLException {
        String sql = "INSERT INTO donors (name, age, blood_group, organ, contact, is_available) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, donor.getName());
            ps.setInt(2, donor.getAge());
            ps.setString(3, donor.getBloodGroup());
            ps.setString(4, donor.getOrgan());
            ps.setString(5, donor.getContact());
            ps.setBoolean(6, donor.isAvailable());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }

    public List<Donor> getAllDonors() throws SQLException {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donors ORDER BY donor_id";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<Donor> getAvailableDonorsByOrgan(String organ) throws SQLException {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE organ = ? AND is_available = TRUE";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, organ);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public boolean markAsUnavailable(int donorId) throws SQLException {
        String sql = "UPDATE donors SET is_available = FALSE WHERE donor_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, donorId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteDonor(int donorId) throws SQLException {
        String sql = "DELETE FROM donors WHERE donor_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, donorId);
            return ps.executeUpdate() > 0;
        }
    }

    private Donor mapRow(ResultSet rs) throws SQLException {
        return new Donor(
                rs.getInt("donor_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("blood_group"),
                rs.getString("organ"),
                rs.getString("contact"),
                rs.getBoolean("is_available")
        );
    }
}