package org.app.chaincode.BlockChain.MerchandiseTrace;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ResponseParser {

	public static TraceChain parseChain(String payload) {
		
		JsonParser parse =new JsonParser(); 
		
		try {
			
			JsonObject json = (JsonObject) parse.parse(payload);  
            String merchandiseID = json.get("merchandiseID").getAsString();
            String owner = json.get("owner").getAsString();
            String manufacturerName = json.get("manufacturer").getAsString();
            String transporterName = json.get("transporter").getAsString();
            String retailerName = json.get("retailer").getAsString();
            String manufacturerTime = json.get("createTime").getAsString();
            String transporterTime = json.get("transportTime").getAsString();
            String retailerTime = json.get("receiveTime").getAsString();

            Manufacturer manufacturer = new Manufacturer(manufacturerName);
            Transporter transporter = new Transporter(transporterName);
            Retailer retailer = new Retailer(retailerName);
            TraceChain chain = new TraceChain();
            chain.setMerchandiseID(merchandiseID);
            chain.setManufacturer(manufacturer);
            chain.setTransporter(transporter);
            chain.setRetailer(retailer);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chain.setManufacturerTime(sdf.parse(manufacturerTime));
            chain.setTransporterTime(sdf.parse(transporterTime));
            chain.setRetailerTime(sdf.parse(retailerTime));
            int length = 0;
            if (owner == "Manufacturer") 
            	length = 1;
            else if (owner == "Transporter") 
            	length = 2;            
            else 
            	length = 3;
       
            chain.setLength(length);
            
            return chain;
			
		} catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (ParseException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
	
	public static ArrayList<TraceChain> parseChains(String payload) {
		
		JsonParser parse =new JsonParser(); 
		
		try {
			ArrayList<TraceChain> chains = new ArrayList<TraceChain>();
			JsonArray jsons = (JsonArray)parse.parse(payload); 
			for (JsonElement json_element: jsons) {
				JsonObject json = (JsonObject)json_element;
				String merchandiseID = json.get("merchandiseID").getAsString();
	            String owner = json.get("owner").getAsString();
	            String manufacturerName = json.get("manufacturer").getAsString();
	            String transporterName = json.get("transporter").getAsString();
	            String retailerName = json.get("retailer").getAsString();
	            String manufacturerTime = json.get("createTime").getAsString();
	            String transporterTime = json.get("transportTime").getAsString();
	            String retailerTime = json.get("receiveTime").getAsString();

	            Manufacturer manufacturer = new Manufacturer(manufacturerName);
	            Transporter transporter = new Transporter(transporterName);
	            Retailer retailer = new Retailer(retailerName);
	            TraceChain chain = new TraceChain();
	            chain.setMerchandiseID(merchandiseID);
	            chain.setManufacturer(manufacturer);
	            chain.setTransporter(transporter);
	            chain.setRetailer(retailer);
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            chain.setManufacturerTime(sdf.parse(manufacturerTime));
	            chain.setTransporterTime(sdf.parse(transporterTime));
	            chain.setRetailerTime(sdf.parse(retailerTime));
	            int length = 0;
	            if (owner == "Manufacturer") 
	            	length = 1;
	            else if (owner == "Transporter") 
	            	length = 2;            
	            else 
	            	length = 3;
	       
	            chain.setLength(length);
	            
	            chains.add(chain);
			}
            
			return chains;
			
		} catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (ParseException e) {
        	e.printStackTrace();
        }
		
		return null;
	}

}
