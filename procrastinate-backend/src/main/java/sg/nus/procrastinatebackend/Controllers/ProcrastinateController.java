package sg.nus.procrastinatebackend.Controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.nus.procrastinatebackend.Models.Uploads;
import sg.nus.procrastinatebackend.Services.DataProcessorService;
import sg.nus.procrastinatebackend.Services.UploadService;
import sg.nus.procrastinatebackend.Services.s3UploadService;

@RestController
@RequestMapping("/api")

public class ProcrastinateController {

    @Autowired
    s3UploadService s3UploadSvc;

    @Autowired
    UploadService uploadSvc;

    @Autowired
    DataProcessorService dataSvc;

    Logger logger = Logger.getLogger(ProcrastinateController.class.getName());

    @PostMapping("/speechToText")
    @PreAuthorize("hasRole('USER') or hasRole('PREMIUM') or hasRole('ADMIN')")
    public ResponseEntity<String>speechToText(
            @RequestPart String username,
            @RequestPart String email,
            @RequestPart MultipartFile audioFile){

        logger.log(Level.INFO, "From procrastinate controller >>>");
        logger.log(Level.INFO, "Username > " + username);
        logger.log(Level.INFO, "Email > " + email);

        Uploads upload = s3UploadSvc.uploadToS3(username, audioFile);
        uploadSvc.saveUpload(upload);

        //Todo send upload to django

        upload = dataSvc.processSpeechToText(upload);

        logger.info("RESULT TIME >>>>>>>>>>>>");
        logger.info(upload.getResult());


        return ResponseEntity.status(HttpStatus.OK).body(upload.getResult());

    }
}
