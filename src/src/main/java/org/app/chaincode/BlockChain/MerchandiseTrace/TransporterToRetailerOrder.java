package org.app.chaincode.BlockChain.MerchandiseTrace;

import java.util.*;

/**
 * 
 */
public class TransporterToRetailerOrder extends Order {

    
    public TransporterToRetailerOrder(String m_ID, Manufacturer manufacturer, Transporter transporter,
			Retailer retailer, Date time) {
		super(m_ID, manufacturer, transporter, retailer, time);
		// TODO Auto-generated constructor stub
	}

	public TransporterToRetailerOrder(String m_ID, Manufacturer manufacturer, Transporter transporter,
			Retailer retailer) {
		super(m_ID, manufacturer, transporter, retailer);
		// TODO Auto-generated constructor stub
	}

	/**
     * @return
     */
    public Role from() {
        // TODO implement here
        return transporter;
        
    }

    /**
     * @return
     */
    public Role to() {
        // TODO implement here
        return retailer;
    }

}