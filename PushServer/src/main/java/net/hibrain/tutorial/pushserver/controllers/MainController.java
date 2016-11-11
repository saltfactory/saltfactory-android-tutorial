package net.hibrain.tutorial.pushserver.controllers;

import net.hibrain.tutorial.pushserver.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by saltfactory on 10/11/2016.
 */
@RestController
public class MainController {

    /**
     * Firebase 프로젝트의 Server Key
     */
    @Value("${SERVER_KEY}")
    private String SERVER_KEY;

    /**
     * Firebase의 FCM Http connection URL
     */
    @Value("${GCM_SERVER_URL}")
    private String GCM_SERVER_URL;


    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private UserDao userDao;


    /**
     * 안드로이드로 부터 토큰정보를 저장하는 메소드
     *
     * @param device
     * @return
     */
    @RequestMapping(value = "/tokens/save", method = RequestMethod.POST)
    @ResponseBody
    int saveToken(@RequestBody Device device) {
        return deviceDao.saveDevice(device);
    }

    /**
     * 데이터베이스에 가지고 있는 모든 디바이스 토큰을 대상으로 메세지를 보내는 메소드
     *
     * @return
     */
    @RequestMapping(value = "/messages/send", method = RequestMethod.POST)
    @ResponseBody
    List sendMessage(@RequestBody Map params) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Authorization", "key=" + SERVER_KEY);

        RestTemplate restTemplate = new RestTemplate();
        List<String> results = new ArrayList<String>();

        String username = (String) params.get("reciever");
        String chatMessage = (String) params.get("message");
        User user = userDao.getUserByUsername(username);

        Message message = new Message();
        message.setTo(user.getToken());
        message.setNotification(new Notification("테스트메세지", chatMessage));


        HttpEntity<Message> entity = new HttpEntity<Message>(message, headers);
        String result = restTemplate.postForObject(GCM_SERVER_URL, entity, String.class);
        results.add(result);

        return results;
    }

    /**
     * 안드로이드로 부터 토큰정보를 저장하는 메소드
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/users/save", method = RequestMethod.POST)
    @ResponseBody
    int saveUser(@RequestBody User user) {
        return userDao.saveUser(user);
    }

}
