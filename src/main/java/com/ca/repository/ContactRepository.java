package com.ca.repository;

import com.ca.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "select * from contact c where contact_id=?1 and status=true", nativeQuery = true)
    Optional<Contact> findByContactId(Long contactId);
}
