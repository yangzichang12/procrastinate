package sg.nus.procrastinatebackend.Models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Uploads {

    private String uploadId;
    private String username;
    private String contentUrl;
    private String contentType;
    private String resultUrl;
    private String result;

    public String getUploadId() {
        return uploadId;
    }
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
    public String getContentUrl() {
        return contentUrl;
    }
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getResultUrl() {
        return resultUrl;
    }
    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Uploads [uploadId=" + uploadId + ", username=" + username + ", contentUrl=" + contentUrl
                + ", contentType=" + contentType + ", resultUrl=" + resultUrl + "]";
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
            .add("uploadId",uploadId)
            .add("username",username)
            .add("contentUrl",contentUrl)
            .add("contentType",contentType)
            .build();
    }



    
}
