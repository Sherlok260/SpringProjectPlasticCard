package com.example.springcardprojectdemo.repository;

import com.example.springcardprojectdemo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "select c from cards c where c.user_email = ?1", nativeQuery = true)
    public Card findByUser_email(String email);

    @Query(value = "select * from cards where user_email = ?1", nativeQuery = true)
    public List<Card> getAllByUser_email(String user_email);

    @Query(value = "select c from cards c where c.number like '__________' || ?1", nativeQuery = true)
    public Card findByNumber(String number);

}
