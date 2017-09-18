package container;

import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public interface FriendsService {
	/*
	 * @return sucess data  
	 * @param payload
	 * Method to link 2 email id's
	 */
	Map<String, Object> addFriends(Map<String, Object> payload);
	/*
	 * @return list of friends  
	 * @param payload
	 * Method to return 
	 */
	Map<String, Object> fetchFriends(Map<String, Object> payload);
	/*
	 * @return list of common Friends  
	 * @param payload
	 * Method to return list of common Friends
	 */
	Map<String, Object> fetchCommonFriends(Map<String, Object> payload);
	/*
	 * @return   
	 * @param payload
	 * Method to subscribe the updates for target 
	 */
	Map<String, Object> subscribeUpdate(Map<String, Object> payload);
	/*
	 * @return   
	 * @param payload
	 * Method to block the updates for target 
	 */
	Map<String, Object> blockUpdate(Map<String, Object> payload);
	/*
	 * @return  list of friend
	 * @param payload
	 * Method to return list of friend subscribed for update
	 */
	Map<String, Object> fetchEmailForUpdate(Map<String, Object> payload);

}
