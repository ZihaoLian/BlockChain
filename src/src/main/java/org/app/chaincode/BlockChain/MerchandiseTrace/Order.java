package org.app.chaincode.BlockChain.MerchandiseTrace;

import java.util.*;

import javax.management.relation.RoleInfo;
import javax.print.attribute.standard.DateTimeAtCompleted;

//import org.apache.commons.collections.keyvalue.TiedMapEntry;

/**
 * 
 */
public abstract class Order {

    /**
     * Default constructor
     */
    public Order(String m_ID, Manufacturer manufacturer, Transporter transporter, Retailer retailer) {
        this.merchandiseID = m_ID;
        this.manufacturer = manufacturer;
        this.transporter = transporter;
        this.retailer = retailer;
        this.time = new Date();
    }

    public Order(String m_ID, Manufacturer manufacturer, Transporter transporter, Retailer retailer, Date time) {
        this.merchandiseID = m_ID;
        this.manufacturer = manufacturer;
        this.transporter = transporter;
        this.retailer = retailer;
        this.time = time;
    }
    
    /**
     * 
     */
    protected String merchandiseID;

    /**
     * 
     */
    protected Date time = null;

    /**
     * 
     */
    protected Manufacturer manufacturer;

    /**
     * 
     */
    protected Transporter transporter;

    /**
     * 
     */
    protected Retailer retailer;

    /**
     * @return
     */
    /*
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
  	*/
    
    
    /**
     * @return
     */
    /*
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
    */
    
    public Role from() {
    	return null;
    }
    
    public Role to() {
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
        return time;
    }

    public void setComfirmTime(Date t){
        time = t;
    }

	public Date getTime() {
		return time;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public Transporter getTransporter() {
		return transporter;
	}

	public Retailer getRetailer() {
		return retailer;
	}
    
    
}