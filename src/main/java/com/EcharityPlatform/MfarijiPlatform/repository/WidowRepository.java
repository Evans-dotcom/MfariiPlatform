package com.EcharityPlatform.MfarijiPlatform.repository;

import com.EcharityPlatform.MfarijiPlatform.Entity.Widow;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface WidowRepository extends ListCrudRepository<Widow,Long> {

    Optional<Object> findByEmailIgnoreCase(String email);

    Optional<Object> findByUsernameIgnoreCase(String username);

}
