package sg.nus.procrastinatebackend.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api/test")
public class testController {

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER') or hasRole('PREMIUM') or hasRole('ADMIN')")
    public ResponseEntity<String> whoami(){
        JsonObject jsonObj = Json.createObjectBuilder()
            .add("Message", "You are the shit")
            .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(jsonObj.toString());
    }


    
}
