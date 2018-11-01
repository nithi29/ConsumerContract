package com.crud.resttemplate.Controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crud.resttemplate.ErrorHandler.ErrorDetails;
import com.crud.resttemplate.User.User;

/**
 *
 */

@SpringBootApplication
@RestController
public class ResttemplateController {
    /**
     * Get users
     */
    @Autowired
    RestTemplate restTemplate;
    User user;

    @RequestMapping(value = "/template/users")
    public String getUserList(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("http://localhost:8080/users", HttpMethod.GET, entity, String.class).getBody();
    }

    /**
     * Delete user
     */
    @RequestMapping(value = "/template/users/{userid}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("userid") int userid) throws NotFoundException{
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<User>(headers);
        return restTemplate.exchange("http://localhost:8080/users/" + userid, HttpMethod.DELETE, entity, String.class)
                .getBody();
    }

    /**
     * post a user
     */
    @RequestMapping(value = "/template/users", method = RequestMethod.POST)
    public User save(@RequestBody User user) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<User>(user, headers);
        RestTemplate restTemplate = new RestTemplate();
        User e = restTemplate.postForEntity("http://localhost:8080/users", entity, User.class).getBody();
        System.out.println(e.toString());
        return user;
    }

    /**
     * Update a user
     */
    @RequestMapping(value = "/template/users/{userid}", method = RequestMethod.PUT)
    public User getById(@PathVariable("userid") int userid, @RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<User>(user, headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/users/" + userid;
        System.out.println(" put url:  " + url + " headers " + entity.toString());
        restTemplate.put(url, entity, User.class);
        System.out.println(user.toString());
        return user;

    }
    /**
     * Get a user by ID
     */
    @RequestMapping(value = "/template/users/{userid}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> fetch(@PathVariable(value = "userid") int userid) {
        System.out.println("Id:" + userid);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
    }

    /**
     * 
     * @param exception if userid doesnt exist.
     * @return UserId doesnt exist.
     */

    @ResponseStatus(value = HttpStatus.NOT_FOUND) //
    @ExceptionHandler(Exception.class)
    public ErrorDetails exception(NotFoundException exception) {
        return new ErrorDetails(exception.getMessage());
    }

}
