package sg.nus.procrastinatebackend.Controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.procrastinatebackend.Models.ERole;
import sg.nus.procrastinatebackend.Models.Roles;
import sg.nus.procrastinatebackend.Models.User;
import sg.nus.procrastinatebackend.Repositories.RoleRepository;
import sg.nus.procrastinatebackend.Repositories.UserRepository;
import sg.nus.procrastinatebackend.Services.UserDetailsImpl;
import sg.nus.procrastinatebackend.dto.JwtResponse;
import sg.nus.procrastinatebackend.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    Logger logger = Logger.getLogger(AuthController.class.getName());

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userRepo = userRepository;
        this.roleRepo = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestPart String username,
                                    @RequestPart String password) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        JwtResponse res = new JwtResponse();
        res.setToken(jwt);
        res.setId(userDetails.getId());
        res.setUsername(userDetails.getUsername());
        res.setRoles(roles);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestPart String username,
                                        @RequestPart String email,
                                        @RequestPart String password) {

        if (userRepo.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username is already taken");
        }
        if (userRepo.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is already taken");
        }
        String hashedPassword = passwordEncoder.encode(password);
        Set<Roles> roles = new HashSet<>();
        Optional<Roles> userRole = roleRepo.findByName(ERole.ROLE_USER);
        if (userRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("role not found");
        }
        roles.add(userRole.get());
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.ok("User registration was successfully");
    }
    
}
