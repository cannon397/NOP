package com.cannon.nop.infrastructure.persistence.eventauth;


import com.cannon.nop.domain.eventauth.EventAuthRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RedisEventAuthRepository extends EventAuthRepository {


}

