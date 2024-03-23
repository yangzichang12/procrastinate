package sg.nus.procrastinatebackend.Services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.procrastinatebackend.Models.Uploads;
import sg.nus.procrastinatebackend.Repositories.UploadRepo;

@Service
public class UploadService {

    @Autowired
    UploadRepo uploadRepo;

    Logger logger = Logger.getLogger(UploadService.class.getName());

    public void saveUpload(Uploads upload){
        
        if(uploadRepo.insertUpload(upload)){
            logger.info("Insert upload is successful");
        } else{
            logger.info("Insert upload failed");
        }
    }
    
}
