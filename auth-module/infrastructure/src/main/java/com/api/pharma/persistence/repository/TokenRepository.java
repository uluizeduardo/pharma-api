package com.api.pharma.persistence.repository;

import com.api.pharma.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {


    @Query(value = """
            select t from TokenEntity t inner join UserEntity u \s
            on t.userEntity.id = u.id \s
            where t.expired = false and (t.expired = false or t.revoked = false) \s
            """)
    List<TokenEntity> findAllValidTokenByUser(UUID userId);

    Optional<TokenEntity> findByToken(String token);
}
