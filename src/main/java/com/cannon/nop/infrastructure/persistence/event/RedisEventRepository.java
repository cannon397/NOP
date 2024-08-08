package com.cannon.nop.infrastructure.persistence.event;


import com.cannon.nop.domain.event.EventRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RedisEventRepository extends EventRepository {

}
