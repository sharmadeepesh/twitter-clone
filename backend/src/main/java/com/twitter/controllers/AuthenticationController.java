package com.twitter.controllers;

import com.twitter.exceptions.EmailAlreadyTakenException;
import com.twitter.exceptions.EmailFailedToSendException;
import com.twitter.exceptions.IncorrectVerificationCodeException;
import com.twitter.exceptions.UserDoesNotExistException;
import com.twitter.models.ApplicationUser;
import com.twitter.models.RegistrationObject;
import com.twitter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<String> handleEmailTaken() {
        return new ResponseEntity<String>("The email you have provided is already in use", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> handleUserDoesNotExist() {
        return new ResponseEntity<String>("The user you want to select doesn not exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailFailedToSendException.class)
    public ResponseEntity<String> handleEmailDoesNotExist() {
        return new ResponseEntity<String>("The email could not be sent.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectVerificationCodeException.class)
    public ResponseEntity<String> handleIncorrectVerification() {
        return new ResponseEntity<String>("The verification code is incorrect", HttpStatus.CONFLICT);
    }

    @PutMapping("/update/phone")
    public ApplicationUser updatePhoneNumber(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String phone = body.get("phone");

        ApplicationUser user = userService.getUserByUsername(username);
        user.setPhone(phone);

        return userService.updateUser(user);
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationObject ro) {
        return userService.registerUser(ro);
    }

    @PostMapping("/email/code")
    public ResponseEntity<String> createEmailVerification(@RequestBody LinkedHashMap<String, String> body) {
        userService.generateEmailVerification(body.get("username"));

        return new ResponseEntity<String>("Verification code created. Email sent.", HttpStatus.OK);
    }

    @PostMapping("/email/verify")
    public ApplicationUser verifyEmail(@RequestBody LinkedHashMap<String, String> body) {

        Long code = Long.parseLong(body.get("code"));
        String username = body.get("username");
        return userService.verifyEmail(username, code);
    }

    @PostMapping("/update/password")
    public ApplicationUser updatePassword(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        return userService.updatePassword(username, password);
    }

}
