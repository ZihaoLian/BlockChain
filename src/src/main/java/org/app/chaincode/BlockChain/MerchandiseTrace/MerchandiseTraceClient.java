package org.app.chaincode.BlockChain.MerchandiseTrace;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.app.client.CAClient;
import org.app.client.ChannelClient;
import org.app.client.FabricClient;
import org.app.config.Config;
import org.app.user.UserContext;
import org.app.util.Util;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;



/**
 * 
 */
public class MerchandiseTraceClient {

	
	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";
	
    /**
     * Default constructor
     */
    public MerchandiseTraceClient() {
    	
    }

    /**
     * 
     */
    private static volatile MerchandiseTraceClient instance = null;

    /**
     * @return
     */
    public static MerchandiseTraceClient getInstance() {
        if(null == instance){
            synchronized(MerchandiseTraceClient.class){
                if(null == instance){
                    instance = new MerchandiseTraceClient();
                }
            }
        }
        return instance;
    }

    /**
     * @param order Order 
     * @return
     */
    public void publishOrder(Order order) {
        // TODO implement here
        try{
            Util.cleanUp();
            String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);

            FabricClient fabClient = new FabricClient(adminUserContext);
            
            ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
            channel.initialize();
            
            String manufacturerName = order.getManufacturer().getName();
            String transporterName = order.getTransporter().getName();
            String retailerName = order.getRetailer().getName();
            String merchandiseID = order.getMerchandiseID();
            String time = order.getTime().toString();
            
            TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
			ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build();
			request.setChaincodeID(ccid);
			request.setFcn("invokeInside");
		    String[] arguments = { manufacturerName, transporterName, retailerName, merchandiseID, time };
			request.setArgs(arguments);
			request.setProposalWaitTime(1000);

			Map<String, byte[]> tm2 = new HashMap<>();
			tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
			tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
			tm2.put("result", ":)".getBytes(UTF_8));
			tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
			request.setTransientMap(tm2);
			Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
			for (ProposalResponse res: responses) {
				Status status = res.getStatus();
				Logger.getLogger(MerchandiseTraceClient.class.getName()).log(Level.INFO,"Publish order on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    /**
     * @param role 
     * @return
     */
    public ArrayList<TraceChain> queryChainByRole(Role role) {
        // TODO implement here
    	try {
            Util.cleanUp();
			String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);
			
			FabricClient fabClient = new FabricClient(adminUserContext);
			
			ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();

			String roleName = role.getName();
			/*
			String roleType = null;
			if (role instanceof Manufacturer)
				roleType = "Manufacturer";
			else if (role instanceof Transporter)
				roleType = "Transporter";
			else
				roleType = "Retailer";
			*/
			
			Thread.sleep(10000);
			String[] args = {roleName};
			Logger.getLogger(MerchandiseTraceClient.class.getName()).log(Level.INFO, "Querying by role - " + args[0]);
			
			Collection<ProposalResponse>  responsesQuery = channelClient.queryByChainCode("fabcar", "queryCar", args);
			for (ProposalResponse pres : responsesQuery) {
				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
				Logger.getLogger(MerchandiseTraceClient.class.getName()).log(Level.INFO, stringResponse);
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return null;
    }

    /**
     * @param merchandiseID 
     * @return
     */
    public TraceChain queryChainByMerchandiseID(String merchandiseID) {
        // TODO implement here
        
    	try {
            Util.cleanUp();
			String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);
			
			FabricClient fabClient = new FabricClient(adminUserContext);
			
			ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();
			
			
			
			Thread.sleep(10000);
			String[] args = {merchandiseID};
			Logger.getLogger(MerchandiseTraceClient.class.getName()).log(Level.INFO, "Querying by merchandiseID - " + args[0]);
			
			Collection<ProposalResponse>  responsesQuery = channelClient.queryByChainCode("fabcar", "queryCar", args);
			for (ProposalResponse pres : responsesQuery) {
				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
				Logger.getLogger(MerchandiseTraceClient.class.getName()).log(Level.INFO, stringResponse);
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return null;
    }

    /**
     * @param role 
     * @param length 
     * @return
     */
    public ArrayList<TraceChain> queryCompletedChainByRole(Role role) {
    
    	ArrayList<TraceChain> chains = queryChainByRole(role);
    	ArrayList<TraceChain> result = new ArrayList<TraceChain>();
    	
    	int length = 0;
    	if(role instanceof Manufacturer)
    		length = 1;
    	else if (role instanceof Transporter)
    		length = 2;
    	else 
    		length = 3;
    	
    	for (TraceChain chain: chains) {
    		if(chain.getLength() >= length) {
    			result.add(chain);
    		}
    	}
    	
    	if(result.size()>0) 
    		return result;
    	else
    		return null;
    }

    /**
     * @param rolename 
     * @param length 
     * @return
     */
    public ArrayList<TraceChain> queryUncompletedChainByRole(Role role) {

    	ArrayList<TraceChain> chains = queryChainByRole(role);
    	ArrayList<TraceChain> result = new ArrayList<TraceChain>();
    	
    	int length = 0;
    	if(role instanceof Manufacturer)
    		length = 1;
    	else if (role instanceof Transporter)
    		length = 2;
    	else 
    		length = 3;
    	
    	for (TraceChain chain: chains) {
    		if(chain.getLength() < length) {
    			result.add(chain);
    		}
    	}
    	
    	if(result.size()>0) 
    		return result;
    	else
    		return null;
    }

    /**
     * @param merchandiseID 
     * @return
     */
    public void confirmOfRetailer(String merchandiseID) {
        // TODO implement here

    	try{
            Util.cleanUp();
            String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);

            FabricClient fabClient = new FabricClient(adminUserContext);
            
            ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
            channel.initialize();
            
            
            TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
			ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build();
			request.setChaincodeID(ccid);
			request.setFcn("invokeInside");
		    String[] arguments = { merchandiseID };
			request.setArgs(arguments);
			request.setProposalWaitTime(1000);

			Map<String, byte[]> tm2 = new HashMap<>();
			tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
			tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
			tm2.put("result", ":)".getBytes(UTF_8));
			tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
			request.setTransientMap(tm2);
			Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
			for (ProposalResponse res: responses) {
				Status status = res.getStatus();
				Logger.getLogger(MerchandiseTraceClient.class.getName()).log(Level.INFO,"Confirmed by Retailer on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return;
    }

    /**
     * @param merchandiseID 
     * @return
     */
    public void confirmOfTransporter(String merchandiseID) {
        // TODO implement here
    	try{
            Util.cleanUp();
            String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);

            FabricClient fabClient = new FabricClient(adminUserContext);
            
            ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
            channel.initialize();
            
            
            TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
			ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build();
			request.setChaincodeID(ccid);
			request.setFcn("invokeInside");
		    String[] arguments = { merchandiseID };
			request.setArgs(arguments);
			request.setProposalWaitTime(1000);

			Map<String, byte[]> tm2 = new HashMap<>();
			tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
			tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
			tm2.put("result", ":)".getBytes(UTF_8));
			tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
			request.setTransientMap(tm2);
			Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
			for (ProposalResponse res: responses) {
				Status status = res.getStatus();
				Logger.getLogger(MerchandiseTraceClient.class.getName()).log(Level.INFO,"Confirmed by Transporter on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return;
    }

}