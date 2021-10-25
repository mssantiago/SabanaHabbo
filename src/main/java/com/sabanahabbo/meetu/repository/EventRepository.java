package com.sabanahabbo.meetu.repository;

import java.util.List;
import java.util.Map;

import com.sabanahabbo.meetu.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select ev.id, date,interest,message,place,time,gr.title from events ev full outer join groups gr on ev.group_id= gr.id", nativeQuery = true)
    List <Map<String, Object>> getAllEvents();

    @Query(value = "select e.id, date, interest, message, place, time from events e INNER JOIN interests int on int.name = e.interest where e.interest in =:PARAM", nativeQuery = true)
    List <Map<String, Object>> getAllEventsByInterest(@Param("PARAM") List<String> PARAM);

    @Query(value = "select e.id, date, message, place, time, gr.title from events e INNER JOIN groups gr on gr.id = e.group_id where e.group_id in (:PARAM)", nativeQuery = true)
    List <Map<String, Object>> getAllEventsByGroups(@Param("PARAM") List<Long> PARAM);

}