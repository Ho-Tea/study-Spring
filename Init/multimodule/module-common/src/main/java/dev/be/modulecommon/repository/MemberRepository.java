package dev.be.modulecommon.repository;


import dev.be.modulecommon.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

}
