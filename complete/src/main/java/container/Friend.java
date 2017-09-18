package container;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Friend {

    @Id
    public String emailId;
    public List<String> subscriberEmailList;
    public List<String> blockEmailList;
    public List<String> friends;

    public Friend() {}

    public Friend(String emailId, List<String> subscriberEmailList, List<String> blockEmailList, List<String> friends) {
        this.emailId             = emailId;
        this.subscriberEmailList = subscriberEmailList;
        this.blockEmailList      = blockEmailList;
        this.friends = friends;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, emailId='%s']",
                emailId, emailId);
    }

}

