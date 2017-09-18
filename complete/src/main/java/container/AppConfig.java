package container;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

@Configuration
public class AppConfig {
	@Bean
	public MongoDbFactory mongoDbFactory() throws UnknownHostException{
		return new SimpleMongoDbFactory(new MongoClientURI("mongodb://alok_test:alok_test@ds141024.mlab.com:41024/public_test"));
	}
	
	@Bean
	public MongoOperations mongoOperations() throws UnknownHostException{
		return new MongoTemplate(mongoDbFactory());
	}
}
