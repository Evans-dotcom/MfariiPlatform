package com.EcharityPlatform.MfarijiPlatform.service;

import com.EcharityPlatform.MfarijiPlatform.Entity.VerificationToken;
import com.EcharityPlatform.MfarijiPlatform.Entity.Widow;
import com.EcharityPlatform.MfarijiPlatform.exception.EmailFailureException;
import com.EcharityPlatform.MfarijiPlatform.exception.UserAlreadyExistsException;
import com.EcharityPlatform.MfarijiPlatform.exception.UserNotVerifiedException;
import com.EcharityPlatform.MfarijiPlatform.model.LoginBody;
import com.EcharityPlatform.MfarijiPlatform.model.RegistrationBody;
import com.EcharityPlatform.MfarijiPlatform.repository.VerificationTokenRepository;
import com.EcharityPlatform.MfarijiPlatform.repository.WidowRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class WidowService {

    private final WidowRepository widowRepository;

    private final VerificationTokenRepository verificationTokenRepository;
    /** The encryption service. */
    private final EncryptionService encryptionService;
    /** The JWT service. */
    private final JWTService jwtService;
    /** The email service. */
    private final EmailService emailService;

    public WidowService(WidowRepository widowRepository, VerificationTokenRepository verificationTokenRepository, EncryptionService encryptionService, JWTService jwtService, EmailService emailService) {
        this.widowRepository = widowRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public Widow registerWidow(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
      if (widowRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
              || widowRepository.findByUsernameIgnoreCase(registrationBody.getUserName()).isPresent()) {
          throw new UserAlreadyExistsException();
      }
      Widow widow = new Widow();
      widow.setEmail(registrationBody.getEmail());
      widow.setUsername(registrationBody.getUserName());
      widow.setAge(registrationBody.getAge());
      widow.setConstituency(registrationBody.getConstituency());
      widow.setCounty(registrationBody.getCounty());
      widow.setFirstName(registrationBody.getFirstName());
      widow.setLastName(registrationBody.getLastName());
      widow.setMiddleName(registrationBody.getMiddleName());
      widow.setNationalID(registrationBody.getNationalID());
      widow.setPhoneNo(registrationBody.getPhoneNo());
      widow.setWard(registrationBody.getWard());
      widow.setLocation(registrationBody.getLocation());
      widow.setVillage(registrationBody.getVillage());
      widow.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
      VerificationToken verificationToken = createVerificationToken(widow);
      emailService.sendVerificationEmail(verificationToken);
     return widowRepository.save(widow);
}

    private VerificationToken createVerificationToken(Widow widow) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(widow));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setWidow(widow);
        widow.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }
    public String loginWidow(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<Object> opWidow = widowRepository.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opWidow.isPresent()) {
            Widow widow= (Widow) opWidow.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), widow.getPassword())) {
                if (widow.isEmailVerified()) {
                    return jwtService.generateJWT(widow);
                } else {
                    List<VerificationToken> verificationTokens = widow.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(widow);
                        verificationTokenRepository.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    /**
     * Verifies a user from the given token.
     * @param token The token to use to verify a user.
     * @return True if it was verified, false if already verified or token invalid.
     */
    @Transactional
    public boolean verifyWidow(String token) {
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            Widow widow = verificationToken.getWidow();
            if (!widow.isEmailVerified()) {
                widow.setEmailVerified(true);
                widowRepository.save(widow);
                verificationTokenRepository.deleteByWidow(widow);

                return true;
            }
        }
        return false;
    }

}
