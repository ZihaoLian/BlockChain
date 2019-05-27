
import java.util.*;

import javax.management.relation.RoleInfo;

/**
 * 
 */
public abstract class Order {

    /**
     * Default constructor
     */
    public Order() {
        

    }

    /**
     * 
     */
    private String merchandiseID;

    /**
     * 
     */
    private Date time;

    /**
     * 
     */
    private Manufacturer manufacturer;

    /**
     * 
     */
    private Transporter transporter;

    /**
     * 
     */
    private Retailer retailer;

    /**
     * @return
     */
    public Role from(Role role, String name){
        role = new Role(name);
        if(role instanceof Manufacturer){
            return (Manufacturer)role;
        }
        else if(role instanceof Transporter){
            return (Transporter)role;
        }
        return (Retailer)role;
    }
  
    /**
     * @return
     */
    public Role to(Role role, String name){
        role = new Role(name);
        if(role instanceof Transporter){
            return (Manufacturer)role;
        }
        else if(role instanceof Retailer){
            return (Transporter)role;
        }
        return null;
    }

    /**
     * @return
     */
    public String getMerchandiseID(){
        return merchandiseID;
    }

    public void setMerchandiseID(String m_ID){
        merchandiseID = m_ID;
    }

    public Date getComfirmTime(){
        time = new Date();
        return time;
    }

    public void setComfirmTime(Date t){
        time = t;
    }
}