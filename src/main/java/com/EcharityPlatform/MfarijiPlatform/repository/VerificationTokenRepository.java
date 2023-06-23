package com.EcharityPlatform.MfarijiPlatform.repository;

import com.EcharityPlatform.MfarijiPlatform.Entity.VerificationToken;
import com.EcharityPlatform.MfarijiPlatform.Entity.Widow;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends ListCrudRepository<VerificationToken,Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByWidow(Widow widow);
}
