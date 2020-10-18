package de.bamamoto.homematic.bridge.gateway;

import de.bamamoto.homematic.bridge.storage.DeviceEntity;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author barmeier
 */
public class ControGateway {

    public void addDevice(DeviceEntity deviceEntity) throws MalformedURLException, IOException {
        
        if (deviceEntity.getChildren().isEmpty()) {                           //ignoring children for now.
            return;
        }
        
        URL url = new URL("http://localhost:8080/device/saveDevice");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        Map<String, String> arguments = new HashMap<>();

        arguments.put("device.controller", "2");
        arguments.put("device.device", deviceEntity.getAddress());
        arguments.put("device.description",deviceEntity.getType());
        arguments.put("device.channel",":1");
        arguments.put("device.id","");
        arguments.put("device.type","1");
        
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : arguments.entrySet()) {
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        
    }

}
