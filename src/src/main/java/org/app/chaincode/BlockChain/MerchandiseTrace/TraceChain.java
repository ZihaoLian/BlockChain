package org.app.chaincode.BlockChain.MerchandiseTrace;

import java.util.*;

/**
 * 
 */
public class TraceChain {

    /**
     * Default constructor
     */
    public TraceChain() {
        
    }

    /**
     * 
     */
    private String merchandiseID;

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
     * 
     */
    private Date manufacturerTime;

    /**
     * 
     */
    private Date transporterTime;

    /**
     * 
     */
    private Date retailerTime;
    
    private int length;

    /**
     * @return
     */
    public int getLength() {
        // TODO implement here
        return length;
    }
    
    public void setLength(int length) {
    	this.length = length;
    }
   

	public String getMerchandiseID() {
		return merchandiseID;
	}

	public void setMerchandiseID(String merchandiseID) {
		this.merchandiseID = merchandiseID;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Transporter getTransporter() {
		return transporter;
	}

	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public Date getManufacturerTime() {
		return manufacturerTime;
	}

	public void setManufacturerTime(Date manufacturerTime) {
		this.manufacturerTime = manufacturerTime;
	}

	public Date getTransporterTime() {
		return transporterTime;
	}

	public void setTransporterTime(Date transporterTime) {
		this.transporterTime = transporterTime;
	}

	public Date getRetailerTime() {
		return retailerTime;
	}

	public void setRetailerTime(Date retailerTime) {
		this.retailerTime = retailerTime;
	}
    
    

}