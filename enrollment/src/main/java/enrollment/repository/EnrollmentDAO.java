package enrollment.repository;

import enrollment.common.Enrollee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Enrollment database
 * @author Michael Kulaga
 */
@Repository
public interface EnrollmentDAO extends MongoRepository<Enrollee, String> {

    //custom database calls go here

}
