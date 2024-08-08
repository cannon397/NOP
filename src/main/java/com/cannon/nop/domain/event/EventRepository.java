package com.cannon.nop.domain.event;


import com.cannon.nop.domain.event.model.Event;
import org.springframework.data.repository.CrudRepository;


public interface EventRepository extends CrudRepository<Event, String> {

}
