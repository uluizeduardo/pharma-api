package com.api.pharma.repository;

import com.api.pharma.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {


    @Query(value = """
            select t from Token t inner join User u \s
            on t.user.id = u.id \s
            where t.expired = false and (t.expired = false or t.revoked = false) \s
            """)
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByToken(String token);
}
