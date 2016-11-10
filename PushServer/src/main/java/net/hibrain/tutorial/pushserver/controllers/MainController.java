package net.hibrain.tutorial.pushserver.controllers;

import net.hibrain.tutorial.pushserver.models.Device;
import net.hibrain.tutorial.pushserver.models.DeviceDao;
import net.hibrain.tutorial.pushserver.models.Message;
import net.hibrain.tutorial.pushserver.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 안드로이드로 부터 토큰정보를 저장하는 메소드
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
     * @return
     */
    @RequestMapping("/messages/send")
    @ResponseBody
    List sendMessage() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Authorization", "key=" + SERVER_KEY);

        RestTemplate restTemplate = new RestTemplate();
        List<String> results = new ArrayList<String>();
        List<Device> devices = deviceDao.findAll();

        for (Device device : devices) {
            Message message = new Message();
            message.setTo(device.getToken());
            message.setNotification(new Notification("테스트메세지", "테스트메세지내용"));

            HttpEntity<Message> entity = new HttpEntity<Message>(message, headers);
            String result = restTemplate.postForObject(GCM_SERVER_URL, entity, String.class);
            results.add(result);
        }

        return results;
    }
}
