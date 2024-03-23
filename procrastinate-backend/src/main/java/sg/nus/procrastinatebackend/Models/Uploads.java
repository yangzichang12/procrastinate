package sg.nus.procrastinatebackend.Models;

public class Uploads {

    private String uploadId;
    private String username;
    private String contentUrl;
    private String contentType;

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
    @Override
    public String toString() {
        return "Uploads [uploadId=" + uploadId + ", username=" + username + ", contentUrl=" + contentUrl
                + ", contentType=" + contentType + "]";
    }


    
}
