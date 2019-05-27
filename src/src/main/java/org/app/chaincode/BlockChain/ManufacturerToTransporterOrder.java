
import java.util.*;

/**
 * 
 */
public class ManufacturerToTransporterOrder extends Order {

    /**
     * Default constructor
     */
    public ManufacturerToTransporterOrder() {
        
    }

    /**
     * @return
     */
    public Role from() {
        Order order = new Order();
        // TODO implement here
        return order.getTransporter();
    }

    /**
     * @return
     */
    public Role to(Role role) {
        // TODO implement here
        return Transporter;
    }

}