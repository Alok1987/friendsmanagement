package container;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Component
@RestController
public class FriendsController {

    private final FriendsService service;
    
    @Autowired
    FriendsController(FriendsService service) {
        this.service = service;
    }
    @RequestMapping(value="/addFriend", method=RequestMethod.POST)
    public Map<String, Object> addFriend(@RequestBody Map<String, Object> payload) {
    	return service.addFriends(payload);
    }
    
    @RequestMapping(value="/fetchFriends", method=RequestMethod.POST)
    public Map<String, Object> fetchFriends(@RequestBody Map<String, Object> payload) {
    	return service.fetchFriends(payload);
    }
    
    @RequestMapping(value="/fetchCommonFriends", method=RequestMethod.POST)
    public Map<String, Object> fetchCommonFriends(@RequestBody Map<String, Object> payload) {
    	return service.fetchCommonFriends(payload);
    }
    @RequestMapping(value="/subscribeUpdate", method=RequestMethod.POST)
    public Map<String, Object> subscribeUpdate(@RequestBody Map<String, Object> payload) {
    	return service.subscribeUpdate(payload);
    }
    @RequestMapping(value="/blockUpdate", method=RequestMethod.POST)
    public Map<String, Object> blockUpdate(@RequestBody Map<String, Object> payload) {
    	return service.blockUpdate(payload);
    }
    @RequestMapping(value="/getEmailListForUpdate", method=RequestMethod.POST)
    public Map<String, Object> getEmailListForUpdate(@RequestBody Map<String, Object> payload) {
    	return service.fetchEmailForUpdate(payload);
    }
}
