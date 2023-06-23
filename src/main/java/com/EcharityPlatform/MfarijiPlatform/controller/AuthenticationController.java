package com.EcharityPlatform.MfarijiPlatform.controller;

import com.EcharityPlatform.MfarijiPlatform.Entity.Widow;
import com.EcharityPlatform.MfarijiPlatform.exception.EmailFailureException;
import com.EcharityPlatform.MfarijiPlatform.exception.UserAlreadyExistsException;
import com.EcharityPlatform.MfarijiPlatform.exception.UserNotVerifiedException;
import com.EcharityPlatform.MfarijiPlatform.model.LoginBody;
import com.EcharityPlatform.MfarijiPlatform.model.LoginResponse;
import com.EcharityPlatform.MfarijiPlatform.model.RegistrationBody;
import com.EcharityPlatform.MfarijiPlatform.service.WidowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

     private final WidowService widowService;

    public AuthenticationController(WidowService widowService) {
        this.widowService = widowService;
    }

    @PostMapping("/register")
    public ResponseEntity registerWidow(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            widowService.registerWidow(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
     * Post Mapping to handle widow logins to provide authentication token.
     * @param loginBody The login information.
     * @return The authentication token if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginWidow(@Valid @RequestBody LoginBody loginBody) {
        String jwt = null;
        try {
            jwt = widowService.loginWidow(loginBody);
        } catch (UserNotVerifiedException ex) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if (ex.isNewEmailSent()) {
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Post mapping to verify the email of an account using the emailed token.
     * @param token The token emailed for verification. This is not the same as an
     *              authentication JWT.
     * @return 200 if successful. 409 if failure.
     */
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if (widowService.verifyWidow(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Gets the profile of the currently logged-in user and returns it.
     * @param widow The authentication principal object.
     * @return The user profile.
     */
    @GetMapping("/me")
    public Widow getLoggedInWidowProfile(@AuthenticationPrincipal Widow widow) {
        return widow;
    }


}
