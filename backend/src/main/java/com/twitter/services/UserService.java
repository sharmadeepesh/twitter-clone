package com.twitter.services;

import com.twitter.exceptions.EmailAlreadyTakenException;
import com.twitter.exceptions.EmailFailedToSendException;
import com.twitter.exceptions.IncorrectVerificationCodeException;
import com.twitter.exceptions.UserDoesNotExistException;
import com.twitter.models.ApplicationUser;
import com.twitter.models.RegistrationObject;
import com.twitter.models.Role;
import com.twitter.repositories.RoleRepository;
import com.twitter.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationUser registerUser(RegistrationObject ro) {
        ApplicationUser user = new ApplicationUser();

        user.setFirstName(ro.getFirstName());
        user.setLastName(ro.getLastName());
        user.setEmail(ro.getEmail());
        user.setDateOfBirth(ro.getDob());

        String name = (user.getFirstName() + user.getLastName());
        boolean nameTaken = true;
        String tempName = "";

        while(nameTaken) {
            tempName = generateUsername(name);
            if (userRepo.findByUsername(tempName).isEmpty())
            {
                nameTaken = false;
            }
        }

        user.setUsername(tempName);
        Set<Role> roles = user.getAuthorities();
        roles.add(roleRepo.findByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepo.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public ApplicationUser getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
    }

    public ApplicationUser updateUser(ApplicationUser user) {
        try {
            return userRepo.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public String generateUsername(String name) {
        long generatedNumber = (long) (Math.floor(Math.random() * 1_000_000_000));
        return (name + generatedNumber);
    }

    public void generateEmailVerification(String username) {
        ApplicationUser user = userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        user.setVerificationCode(generateVerificationCode());

        try {
            this.mailService.sendEmail(user.getEmail(), "Your verification code is here", "The verification code is " + user.getVerificationCode());
            userRepo.save(user);
        } catch (Exception e) {
            throw new EmailFailedToSendException();
        }
    }

    private long generateVerificationCode() {
        return (long) Math.floor(Math.random() * 100_000_000);
    }

    public ApplicationUser verifyEmail(String username, Long code) {
        ApplicationUser user = userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        if (code.equals(user.getVerificationCode())) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            return userRepo.save(user);
        }
        else {
            throw new IncorrectVerificationCodeException();
        }
    }

    public ApplicationUser updatePassword(String username, String password) {
        ApplicationUser user = userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        return userRepo.save(user);


    }
}
