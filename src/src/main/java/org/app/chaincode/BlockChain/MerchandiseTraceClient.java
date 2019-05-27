
import java.util.*;
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
import MerchandiseTrace.*;

/**
 * 
 */
public class MerchandiseTraceClient {

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
		} catch (Exception e) {
			e.printStackTrace();
		}
        return;
    }

    /**
     * @param role 
     * @return
     */
    public ArrayList<TraceChain> queryChainByRole(Role role) {
        // TODO implement here

        return null;
    }

    /**
     * @param merchandiseID 
     * @return
     */
    public TraceChain queryChainByMerchandiseID(String merchandiseID) {
        // TODO implement here
        
        queryByTransactionId(merchandiseID);
        return null;
    }

    /**
     * @param role 
     * @param length 
     * @return
     */
    public ArrayList<TraceChain> queryCompletedChainByRole(Role role) {
        // TODO implement here

        return null;
    }

    /**
     * @param rolename 
     * @param length 
     * @return
     */
    public ArrayList<TraceChain> queryUncompletedChainByRole(String rolename, int length) {
        // TODO implement here

        return null;
    }

    /**
     * @param merchandiseID 
     * @return
     */
    public void confirmOfRetailer(String merchandiseID) {
        // TODO implement here

        return;
    }

    /**
     * @param merchandiseID 
     * @return
     */
    public void confirmOfTransporter(String merchandiseID) {
        // TODO implement here

        return;
    }

}