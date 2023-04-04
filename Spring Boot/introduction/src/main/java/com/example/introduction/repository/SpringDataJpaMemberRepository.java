package com.example.introduction.repository;

import com.example.introduction.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //JPQL select m from Member m where m.name = ?
    Optional<Member> findByName(String name);
}
