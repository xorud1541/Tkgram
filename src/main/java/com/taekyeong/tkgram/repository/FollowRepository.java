package com.taekyeong.tkgram.repository;

import com.taekyeong.tkgram.entity.Follow;
import com.taekyeong.tkgram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromAndTo(User from, User to);
}
