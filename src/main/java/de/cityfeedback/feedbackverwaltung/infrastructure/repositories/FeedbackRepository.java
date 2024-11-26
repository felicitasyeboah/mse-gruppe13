package de.cityfeedback.feedbackverwaltung.infrastructure.repositories;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

  @Query("SELECT fa FROM Feedback fa WHERE fa.citizenId = :citizenId")
  List<Feedback> findAllByCitizenId(@Param("citizenId") Long citizenId);

  @Modifying
  @Query("update Feedback f set f.updatedAt = CURRENT_TIMESTAMP where f.id = :feedback")
  Feedback updateFeedback(@Param("feedback") Feedback feedback);
}
