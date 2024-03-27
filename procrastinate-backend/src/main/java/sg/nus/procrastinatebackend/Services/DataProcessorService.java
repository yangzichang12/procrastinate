package sg.nus.procrastinatebackend.Services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.procrastinatebackend.Models.Uploads;
import sg.nus.procrastinatebackend.dto.JwtResponse;

@Service
public class DataProcessorService {

    Logger logger = Logger.getLogger(DataProcessorService.class.getName());
    private String apiUrl = "http://localhost:8000/api";

    @SuppressWarnings("null")
    public void sendJwtToDataprocesser(JwtResponse jwtResp){
        String url = UriComponentsBuilder.fromUriString(apiUrl)
                     .path("/authToken")
                     .toUriString();
        logger.info("Sending JWT to data processor >>>");
        logger.info(jwtResp.getToken());

        JsonObject jsonObject = Json.createObjectBuilder()
            .add("token",jwtResp.getToken())
            .add("id",jwtResp.getUsername())
            .build();

        RequestEntity<String> req = RequestEntity
            .post(url)
            .body(jsonObject.toString());
        logger.info(req.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        // JsonObject respBodyJsonObject = createJsonObj(resp);

        logger.info("Response form data processor >>>");
        logger.info("JT token: " + resp.getBody().toString());
    }
    

    @SuppressWarnings("null")
    public Uploads processSpeechToText(Uploads upload, String jwtToken){

        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/speechToText")
            .toUriString();

        JsonObject uploadJson = upload.toJson(jwtToken);
        
        logger.info("From Data service >>>");
        logger.info("url to Django app: " + url);
        logger.info(uploadJson.toString());

        RequestEntity<String> req = RequestEntity
                .post(url)
                .body(uploadJson.toString());
        logger.info(req.toString());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        JsonObject respBodyJsonObject = createJsonObj(resp);

        upload.setResultUrl(respBodyJsonObject.getString("result_url"));
        upload.setResult(respBodyJsonObject.getString("result"));

        return upload;

    }
    public JsonObject createJsonObj(ResponseEntity<String> resp) {

    JsonObject result = null;

    try(@SuppressWarnings("null")
    InputStream file = new ByteArrayInputStream(resp.getBody().getBytes())){
        JsonReader reader = Json.createReader(file);
        result = reader.readObject();
    } catch (IOException ex){
        ex.printStackTrace();
    }

    JsonObject responseBody = result;

    return responseBody;

    }

    
}
