import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates blood-group donor -> recipient compatibility rules.
 * Kept as a separate utility class instead of hardcoding if/else chains
 * in the matching logic - a good interview talking point on separation of concerns.
 */
public class BloodCompatibility {

    private static final Map<String, List<String>> CAN_DONATE_TO = new HashMap<>();

    static {
        CAN_DONATE_TO.put("O-",  List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+")); // universal donor
        CAN_DONATE_TO.put("O+",  List.of("O+", "A+", "B+", "AB+"));
        CAN_DONATE_TO.put("A-",  List.of("A-", "A+", "AB-", "AB+"));
        CAN_DONATE_TO.put("A+",  List.of("A+", "AB+"));
        CAN_DONATE_TO.put("B-",  List.of("B-", "B+", "AB-", "AB+"));
        CAN_DONATE_TO.put("B+",  List.of("B+", "AB+"));
        CAN_DONATE_TO.put("AB-", List.of("AB-", "AB+"));
        CAN_DONATE_TO.put("AB+", List.of("AB+")); // can only donate to AB+
    }

    public static boolean isCompatible(String donorBloodGroup, String recipientBloodGroup) {
        List<String> compatibleRecipients = CAN_DONATE_TO.get(donorBloodGroup.toUpperCase());
        return compatibleRecipients != null && compatibleRecipients.contains(recipientBloodGroup.toUpperCase());
    }
}