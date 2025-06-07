package com.example.online_grocery_delivery.Controller;

import com.example.online_grocery_delivery.Dto.AuthRequest;
import com.example.online_grocery_delivery.Dto.AuthResponse;
import com.example.online_grocery_delivery.Dto.UserRegisterDto;
import com.example.online_grocery_delivery.Entity.User;
import com.example.online_grocery_delivery.Repo.UserRepo;
import com.example.online_grocery_delivery.Services.UserDetailsImp;
import com.example.online_grocery_delivery.Utill.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsImp userDetailsImp;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()));
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid!");
        }
        User user = userRepo.findByUserName(request.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        final String token = jwtUtil.generateToken(user.getUserName(),user.getRole());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto request){
        if (userRepo.existsByUserName(request.getUserName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken!");
        }
        User newUser = new User();
        newUser.setUserName(request.getUserName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("ROLE_USER");

        userRepo.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfuly!");
    }
}
