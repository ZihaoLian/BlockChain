
import java.util.*;

/**
 * 
 */
public class TransporterToRetailerOrder extends Order {

    /**
     * Default constructor
     */
    public TransporterToRetailerOrder() {
        
    }

    /**
     * @return
     */
    public Role from(Role role) {
        // TODO implement here
        return (Transporter) (role);
        
    }

    /**
     * @return
     */
    public Role to(Role role) {
        // TODO implement here
        return Retailer(role);
    }

}