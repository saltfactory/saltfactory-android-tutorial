package net.hibrain.tutorial.pushserver.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by saltfactory on 10/11/2016.
 */
@Component
public class DeviceDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DeviceDao(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  devices(id INTEGER PRIMARY KEY AUTOINCREMENT, token varchar(255))");

    }

    public List<Device> findAll() {
        List<Device> devices = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from devices");
        for (Map row : rows) {
            Device device = new Device();
            device.setToken((String) row.get("token"));
            devices.add(device);
        }

        return devices;
    }

    public int saveDevice(Device device) {
        return jdbcTemplate.update("insert into devices (token) values (?)", new Object[]{device.getToken()});

    }
}
