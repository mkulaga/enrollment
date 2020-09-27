package enrollment.service;

import enrollment.common.Dependent;
import enrollment.common.Enrollee;
import enrollment.exceptions.ResourceAlreadyExistsException;
import enrollment.exceptions.ResourceDoesNotExistException;
import enrollment.repository.EnrollmentDAO;
import enrollment.validation.EnrollmentValidator;
import org.springframework.stereotype.Service;

/**
 * Services for Adding, Modifying, and Deleting Dependents
 * @author Michael Kulaga
 */
@Service
public class DependentService {

    public final EnrollmentDAO enrollmentDAO;
    public final EnrollmentValidator validator;

    /**
     * Default Constructor for DependentService
     * @param enrollmentDAO the EnrollmentDAO to use
     */
    public DependentService(EnrollmentDAO enrollmentDAO,
                            EnrollmentValidator validator) {
        this.enrollmentDAO = enrollmentDAO;
        this.validator = validator;
    }

    /**
     * Adds a new Dependent to an existing Enrollee, as long as the Dependent does not already exist
     *
     * @param addDependent the new Dependent to add to the Enrollee
     * @param enrolleeId the Id of the existing Enrollee
     */
    public void addDependent(Dependent addDependent, String enrolleeId) {

        this.validateDependent(addDependent, true);

        Enrollee existingEnrollee = this.enrollmentDAO.findById(enrolleeId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Unable To Add Dependent, Enrollee Does Not Exist For Id: " + enrolleeId));

        Dependent existingDependent = this.retrieveDependentFromEnrollee(existingEnrollee, addDependent.getId());

        if (existingDependent == null) {
            existingEnrollee.getDependents().add(addDependent);
        } else {
            throw new ResourceAlreadyExistsException("Unable To Add Dependent, Dependent Already Exists For Id: " + addDependent.getId());
        }

        this.enrollmentDAO.save(existingEnrollee);

    }

    /**
     * Modify an existing Dependent, as long as that Dependent already exists for the Enrollee
     *
     * @param modifiedDependent the modified Dependent
     * @param enrolleeId the Id of the Enrollee with the Dependent to modify
     * @param dependentId the Id of the Dependent to modify
     */
    public void modifyDependent(Dependent modifiedDependent, String enrolleeId, String dependentId) {

        this.validateDependent(modifiedDependent, false);

        Enrollee existingEnrollee = this.enrollmentDAO.findById(enrolleeId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Unable To Modify Dependent, Enrollee Does Not Exist For Id: " + enrolleeId));

        modifiedDependent.setId(dependentId);

        Dependent existingDependent = this.retrieveDependentFromEnrollee(existingEnrollee, dependentId);

        if (existingDependent != null) {
            existingEnrollee.getDependents().remove(existingDependent);
            existingEnrollee.getDependents().add(modifiedDependent);
        } else {
            throw new ResourceDoesNotExistException("Unable To Modify Dependent, Dependent Does Not Exist For Id: " + dependentId);
        }

        this.enrollmentDAO.save(existingEnrollee);

    }

    /**
     * Deletes an existing Dependent, as long as that Dependent already exists for the Enrollee
     *
     * @param enrolleeId the Id of the Enrollee with the Dependent to modify
     * @param dependentId the Id of the Dependent to modify
     */
    public void deleteDependent(String enrolleeId, String dependentId) {

        Enrollee existingEnrollee = this.enrollmentDAO.findById(enrolleeId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Unable To Delete Dependent, Enrollee Does Not Exist For Id: " + enrolleeId));

        Dependent existingDependent = this.retrieveDependentFromEnrollee(existingEnrollee, dependentId);

        if (existingDependent != null) {
            existingEnrollee.getDependents().remove(existingDependent);
        } else {
            throw new ResourceDoesNotExistException("Unable To Delete Dependent, Dependent Does Not Exist For Id: " + dependentId);
        }

        this.enrollmentDAO.save(existingEnrollee);

    }

    /**
     * Helper Method to retrieve a Dependent from an Enrollee based on the Id of the Dependent
     *
     * @param enrollee the Enrollee to retrieve the Dependent from
     * @param dependentId the Id of the Dependent
     * @return the Dependent that matches the Id passed in
     */
    private Dependent retrieveDependentFromEnrollee(Enrollee enrollee, String dependentId) {

        return enrollee.getDependents().stream()
                .filter(existingDependent -> existingDependent.getId().equals(dependentId))
                .findAny()
                .orElse(null);

    }

    /**
     * Helper method to validate an Dependent
     *
     * @param dependent the Dependent to validate
     * @param newEntity whether it is a new entity in the database or not
     */
    private void validateDependent(Dependent dependent, boolean newEntity) {

        this.validator.entityValidator(dependent.getId(), dependent.getName(), dependent.getDateOfBirth(), newEntity);

    }

}
