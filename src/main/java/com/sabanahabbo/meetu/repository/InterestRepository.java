package com.sabanahabbo.meetu.repository;

import java.util.List;
import java.util.Map;

import com.sabanahabbo.meetu.model.Interest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    
    @Query(value = "select st.name, st.last_name from students st INNER JOIN student_interest stint ON stint.student_id = st.id INNER JOIN interests int ON stint.interest_id = int.id where stint.interest_id =:PARAM", nativeQuery = true)
    List <Map<String, Object>> getAllStudentsByInterest(@Param("PARAM") Long PARAM);
    
    @Query(value = "select int.id, int.name from interests int inner join student_interest stint on stint.interest_id=int.id inner join students st on st.id=stint.student_id where st.id =:PARAM", nativeQuery = true)
    List <Map<String, Object>> getAllInterestByStudent(@Param("PARAM") Long PARAM);

}
