/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package container;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import container.AppConfig;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FriendsControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
   /* private final FriendsService service;*/
    
    
    String addFriendJsonData                    = "{\"friends\":[\"andy@example.com\",\"john@example\"]}";
    String fetchFriendsJsonData                 = "{\"email\": \"andy@example.com\"}";
    String addFriendJsonData1                   = "{\"friends\":[\"andy@example.com\",\"common@example\"]}";
    String addFriendJsonData2                   = "{\"friends\":[\"john@example.com\",\"common@example\"]}";
    String fetchCommonFriendsJsonData           = "{\"friends\":[\"andy@example.com\",\"john@example\"]}";
    String subscribeUpdateJsonData              = "{\"requestor\": \"lisa@example.com\",\"target\": \"john@example.com\"}";
    String blockUpdateJsonData                  = "{\"requestor\": \"andy@example.com\",\"target\": \"john@example.com\"}";
    String getEmailListForUpdateJsonData        = "{\"sender\":  \"john@example.com\",\"text\": \"Hello World! kate@example.com\"}";

    
/*    @Autowired
    FriendsControllerTests(FriendsService service) {
        this.service = service;
    }*/
    
    @Test
    public void addFriends() throws Exception{
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/addFriend")
				.accept(MediaType.APPLICATION_JSON).content(addFriendJsonData)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    
    @Test
    public void fetchFriends() throws Exception{
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/fetchFriends")
				.accept(MediaType.APPLICATION_JSON).content(fetchFriendsJsonData)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    @Test
    public void fetchCommonFriends() throws Exception{
    	

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/fetchCommonFriends")
				.accept(MediaType.APPLICATION_JSON).content(fetchCommonFriendsJsonData)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    
    @Test
    public void subscribeUpdate() throws Exception{
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/subscribeUpdate")
				.accept(MediaType.APPLICATION_JSON).content(subscribeUpdateJsonData)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    @Test
    public void blockUpdate() throws Exception{
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/blockUpdate")
				.accept(MediaType.APPLICATION_JSON).content(blockUpdateJsonData)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    @Test
    public void getEmailListForUpdate() throws Exception{
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/getEmailListForUpdate")
				.accept(MediaType.APPLICATION_JSON).content(getEmailListForUpdateJsonData)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


}
