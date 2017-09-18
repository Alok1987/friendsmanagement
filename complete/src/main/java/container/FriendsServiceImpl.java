/**
 * 
 */
package container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author alok
 *
 */
@Service
@Component
public class FriendsServiceImpl implements FriendsService {
	
    private final FriendsRepository repository;
    
    @Autowired
    FriendsServiceImpl(FriendsRepository repository) {
        this.repository = repository;
    }
    
	@Autowired
	MongoOperations mongoOperations;

	@Override
	public Map<String, Object> addFriends(Map<String, Object> payload) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if(payload != null && payload.get("friends") != null){
			List<String> friendList = (List<String>)payload.get("friends");
			if(friendList.size()<2){
				responseMap.put("error", "please enter minimum 2 emailId's");
			}
			else{
				if(!friendList.get(0).equals(friendList.get(1))){
					Friend firstFriend  = repository.findOne(friendList.get(0));
					Friend secondFriend = repository.findOne(friendList.get(1));
					if(firstFriend == null){//First time entry
						repository.save(new Friend(friendList.get(0), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(Arrays.asList(friendList.get(1)))));
					}else{//update
						List<String> existingFriendList = firstFriend.friends;
						List<String> blockedList        = firstFriend.blockEmailList;
						if(!existingFriendList.contains(friendList.get(1))){//avoiding duplicate entry
							if(!(blockedList != null && !blockedList.isEmpty() && blockedList.contains(friendList.get(1)))){//If updates are blocked then no friend connection
								existingFriendList.add(friendList.get(1));
							}
						}
						repository.save(new Friend(friendList.get(0), new ArrayList<String>(), new ArrayList<String>(), existingFriendList));
					}
					if(secondFriend == null){//First Time Entry
						repository.save(new Friend(friendList.get(1), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(Arrays.asList(friendList.get(0)))));
					}else{//update
						List<String> existingFriendList = secondFriend.friends;
						List<String> blockedList        = secondFriend.blockEmailList;
						if(!existingFriendList.contains(friendList.get(0))){//avoiding duplicate entry
							if(!(blockedList != null && !blockedList.isEmpty() && blockedList.contains(friendList.get(0)))){//If updates are blocked then no friend connection
								existingFriendList.add(friendList.get(0));
							}
						}
						repository.save(new Friend(friendList.get(1), new ArrayList<String>(), new ArrayList<String>(), existingFriendList));
					}
					responseMap.put("success", true);
				}else{
					responseMap.put("error", "please enter two different emailId's");
				}
			}
		}
    	
       return responseMap;
	}

	@Override
	public Map<String, Object> fetchFriends(Map<String, Object> payload) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
    	String emailId 					= (String)payload.get("email");
    	Friend friend 					= repository.findOne(emailId);
    	if(friend != null && !friend.friends.isEmpty()){
	    	responseMap.put("friends", friend.friends);
	    	responseMap.put("success", true);
	    	responseMap.put("count", 1);
    	}else{
    		responseMap.put("error", "No friends for provided emailId");
    	}
    	
       return responseMap;
	}

	@Override
	public Map<String, Object> fetchCommonFriends(Map<String, Object> payload) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<String> listEmail  = (List<String>)payload.get("friends");
		List<Friend> friendList = repository.findByFriends(listEmail);
		List<String> commonList = new ArrayList<String>();
		for(Friend friend: friendList){
			commonList.add(friend.emailId);
		}
    	responseMap.put("success", true);
    	responseMap.put("friends", commonList);
    	responseMap.put("count", commonList.size());
    	return responseMap;
	}

	@Override
	public Map<String, Object> blockUpdate(Map<String, Object> payload) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
    	String requestorEmailId 		= (String)payload.get("requestor");
    	String targetEmailId 			= (String)payload.get("target");
		Friend requestor                = repository.findOne(requestorEmailId);

		if(requestor != null){
			List<String> subscriberList = requestor.subscriberEmailList;
			List<String> blockedList    = requestor.blockEmailList;
			List<String> friends        = requestor.friends;
			if(blockedList == null){
				blockedList = new ArrayList<String>();
			}
			if(!blockedList.contains(targetEmailId)){
				blockedList.add(targetEmailId);
			}
			if(subscriberList != null && !subscriberList.isEmpty() && subscriberList.contains(targetEmailId)){
				subscriberList.remove(targetEmailId);
			}
			
			/*if(friends != null && !friends.isEmpty() && friends.contains(targetEmailId)){
				friends.remove(targetEmailId);
			}*/
			repository.save(new Friend(requestorEmailId, subscriberList, blockedList, friends));
		}

    	responseMap.put("success", true);
    	return responseMap;	
		
	}
	@Override
	public Map<String, Object> subscribeUpdate(Map<String, Object> payload) {
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	String requestorEmailId 		= (String)payload.get("requestor");
    	String targetEmailId 			= (String)payload.get("target");
		Friend target                   = repository.findOne(targetEmailId);

		if(target != null){
			List<String> subscriberList = target.subscriberEmailList;
			List<String> blockedList    = target.blockEmailList;
			if(subscriberList == null){
				subscriberList = new ArrayList<String>();
			}
			if(!subscriberList.contains(requestorEmailId)){
				subscriberList.add(requestorEmailId);
			}
			if(blockedList != null && !blockedList.isEmpty() && blockedList.contains(requestorEmailId)){
				blockedList.remove(requestorEmailId);
			}
			repository.save(new Friend(targetEmailId, subscriberList, blockedList, target.friends));
		}

    	responseMap.put("success", true);
    	return responseMap;	
	}
	

	@Override
	public Map<String, Object> fetchEmailForUpdate(Map<String, Object> payload) {
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	try{
	    	String senderEmailId 		    = (String)payload.get("sender");
	    	String text 			        = (String)payload.get("text");
	    	List<String> allSubscriberList  = new ArrayList<String>();
	    	
	    	Query friendQuery = new Query();
	    	friendQuery.addCriteria(Criteria.where("friends").in(senderEmailId));
	    	friendQuery.addCriteria(Criteria.where("blockEmailList").nin(senderEmailId));
	    	// Add a and criteria for e.g. friends list contains && !blockedList 
	    	List<Friend> friendList = mongoOperations.find(friendQuery, Friend.class);
	    	
	    	Query subscriberQuery = new Query();
	    	subscriberQuery.addCriteria(Criteria.where("subscriberEmailList").in(senderEmailId));
	    	List<Friend> subscriberList = mongoOperations.find(subscriberQuery, Friend.class);
	    	
	    	for(Friend friend:friendList){
	    		allSubscriberList.add(friend.emailId);
	    	}
	    	for(Friend friend:subscriberList){
	    		allSubscriberList.add(friend.emailId);
	    	}
	    	if(!StringUtils.isEmpty(text)){
	    		Set<String> emailIdsFromtext = fetchEmailListFromString(text);
	    		if(!emailIdsFromtext.isEmpty()){
	    			allSubscriberList.addAll(emailIdsFromtext);
	    		}
	    	}
	    	
	    	responseMap.put("success", true);
	    	responseMap.put("recipients", allSubscriberList);
	    	responseMap.put("count", allSubscriberList.size());
    	}catch(Exception ex){
    		System.out.println(ex);
    	}
    	return responseMap;
	}
	
	private Set<String> fetchEmailListFromString(String text){
		Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
		Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher("Hello World! kate@example.com");
		Set<String> emails = new HashSet<String>();
		while(matcher.find()) {
		  emails.add(matcher.group());
		}
		return emails;
			
	}

}
