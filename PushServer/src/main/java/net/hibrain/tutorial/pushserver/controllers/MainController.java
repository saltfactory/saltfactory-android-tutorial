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
     * 사용자로부터 reciver 정보를 가지는 메세지를 전송하도록 요청하는 메소드
     * 안드로이드로 부터 전송할 메세지는 JSON 형태로 넘어오는데 이것은 Map으로 매핑이된다
     * JSON 의 형태는 {"receiver":"수신자", "message":"메세지"} 와 같은 형태로 들어오게 된다.
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
        User user = userDao.getUserByUsername(username); // 메세지 전송을 위해 수신자 정보를 데이터베이스에서 조회하여 수신자의 디바이스 토큰을 획득한다.

        Message message = new Message();
        message.setTo(user.getToken()); // 데이터베이스에서 획득한 수신자 토큰 정보를 FCM 푸시를 요청하는 JSON에 추가한다.
        message.setNotification(new Notification("테스트메세지", chatMessage)); // 송신자로 부터 받은 메세지를 FCM 푸시 요청아는 JSON에 추가한다.


        HttpEntity<Message> entity = new HttpEntity<Message>(message, headers);
        String result = restTemplate.postForObject(GCM_SERVER_URL, entity, String.class); // FCM HTTP connection Sever로 푸시 전송을 요청한다
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
