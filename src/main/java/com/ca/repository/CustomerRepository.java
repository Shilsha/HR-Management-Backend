package com.ca.repository;

import com.ca.entity.Customer;
import com.ca.entity.User;
import com.ca.model.response.CustomerResponseDto;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select * from customer c where ca_id=?1",nativeQuery = true)
    List<Customer> findByCAId(Long caId);

    @Query(value = "select * from customer c where user_id=?1",nativeQuery = true)
    Customer findUserId(Long id);

    @Query(value = "select * from customer c where user_id=?1", nativeQuery = true)
    Optional<Customer> findByUserId(Long userId);

    @Query(value = "select * from customer c where ca_id=?1",nativeQuery = true)
    Page<Customer> findByCAid(Long caId, Pageable pageable);


//    SELECT c.id, u.first_name, u.email
//    FROM customer c
//    Inner JOIN user u ON c.user_id  = u.id WHERE ca_id = 225 AND u.first_name LIKE 'a%';


//    .id(c.getId())
//            .userId(user.getId())
//            .caId(caUserId)
//                    .caName(user1.getFirstName())
//            .firstName(user.getFirstName())
//            .lastName(user.getLastName())
//            .email(user.getEmail())
//            .address(user.getAddress())
//            .mobile(user.getMobile())
//            .phone(user.getPhone())
//            .panCardNumber(user.getPanCardNumber())
//            .gender(user.getGender())



 //TODO

//    @Query(value = "select * from customer c where id=?1 and userId=?2",nativeQuery = true)
//    Optional<Customer> findById(Long id, Long userId);
}
