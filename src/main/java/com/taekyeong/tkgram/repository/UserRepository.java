package com.taekyeong.tkgram.repository;

import com.taekyeong.tkgram.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmailAndPassword(String email, String password);
}
