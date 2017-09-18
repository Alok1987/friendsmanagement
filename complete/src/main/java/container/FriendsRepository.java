package container;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface FriendsRepository extends MongoRepository<Friend, String> {

    public List<Friend> findByFriends(List<String> friends);
    public List<Friend> findBySubscriberEmailList(List<String> friends);


}
