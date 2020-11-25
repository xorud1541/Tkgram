package com.taekyeong.tkgram.repository;

import com.taekyeong.tkgram.entity.Follow;
import com.taekyeong.tkgram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromAndTo(User from, User to);
    List<Follow> findByFrom(User from);
}
