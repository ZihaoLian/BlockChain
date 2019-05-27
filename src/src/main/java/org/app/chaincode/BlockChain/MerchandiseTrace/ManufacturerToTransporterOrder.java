package org.app.chaincode.BlockChain.MerchandiseTrace;

import java.util.*;

/**
 * 
 */
public class ManufacturerToTransporterOrder extends Order {

    public ManufacturerToTransporterOrder(String m_ID, Manufacturer manufacturer, Transporter transporter,
			Retailer retailer, Date time) {
		super(m_ID, manufacturer, transporter, retailer, time);
		// TODO Auto-generated constructor stub
	}



	public ManufacturerToTransporterOrder(String m_ID, Manufacturer manufacturer, Transporter transporter,
			Retailer retailer) {
		super(m_ID, manufacturer, transporter, retailer);
		// TODO Auto-generated constructor stub
	}

	/**
     * @return
     */
	@Override
    public Role from() {
        return manufacturer;
    }

    /**
     * @return
     */
    @Override
    public Role to() {
        return transporter;
    }

}