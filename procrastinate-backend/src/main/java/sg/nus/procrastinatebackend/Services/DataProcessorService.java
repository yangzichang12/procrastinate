package sg.nus.procrastinatebackend.Services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.procrastinatebackend.Models.Uploads;

@Service
public class DataProcessorService {

    Logger logger = Logger.getLogger(DataProcessorService.class.getName());
    private String apiUrl = "http://localhost:8000/api";
    

    @SuppressWarnings("null")
    public Uploads processSpeechToText(Uploads upload){

        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/speechToText")
            .toUriString();

        JsonObject uploadJson = upload.toJson();
        
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
