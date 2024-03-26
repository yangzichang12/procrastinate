package sg.nus.procrastinatebackend.Services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import sg.nus.procrastinatebackend.Models.Uploads;

@Service
public class s3UploadService {

    @Autowired
    AmazonS3 s3;

    Logger logger = Logger.getLogger(s3UploadService.class.getName());

    public Uploads uploadToS3(String username, MultipartFile file){

        logger.info("From uploadToS3 Service >>>");

        String fileId = UUID.randomUUID().toString().substring(0,8);
        String bucketName = "procrastinatingbucket";
        Map<String, String> userCustomMetadata = new HashMap<>();
        userCustomMetadata.put("uploader", username);
        logger.info("Bucket name: " + bucketName);
        logger.info("Uploader: "+ userCustomMetadata.get("uploader"));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userCustomMetadata);
        logger.info("Content Type: " + metadata.getContentType());
        logger.info("Content length: " + metadata.getContentLength());

        try{
            PutObjectRequest putReq = new PutObjectRequest(bucketName, "Procrastinate/%s/upload/%s".formatted("Procrastinate "+username, fileId),
            file.getInputStream(),metadata);
            putReq.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);
        } catch (IOException ex) {
            logger.log(Level.INFO, "Error in s3 upload: " + ex.getMessage());
        }

        String url = s3.getUrl(bucketName, "Procrastinate/%s/upload/%s".formatted("Procrastinate "+username, fileId)).toString();
        logger.info("url: " + url);

        Uploads upload = new Uploads();
        upload.setUsername(username);
        upload.setContentType(metadata.getContentType());
        upload.setContentUrl(url);
        upload.setUploadId(fileId);

        logger.info(upload.toString());

        logger.info("End of uploadToS3 Service logs");

        return upload;

    }
    
}
