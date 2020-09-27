package enrollment.service;

import enrollment.common.Dependent;
import enrollment.common.Enrollee;
import enrollment.exceptions.ResourceAlreadyExistsException;
import enrollment.exceptions.ResourceDoesNotExistException;
import enrollment.repository.EnrollmentDAO;
import enrollment.validation.EnrollmentValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for the DependentService class
 * @author Michael Kulaga
 */
public class DependentServiceTest {

    @Mock
    private EnrollmentDAO enrollmentDAO;

    @Mock
    private EnrollmentValidator enrollmentValidator;

    private DependentService dependentService;

    /**
     * Setting up mocks
     */
    @Before
    public void setUp() {
        initMocks(this);

        this.dependentService = new DependentService(enrollmentDAO, enrollmentValidator);
    }

    @Test
    public void addDependentTest() {

        Enrollee enrollee = this.createEnrollee();
        Dependent dependent = this.createDependent();

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.of(enrollee));

        this.dependentService.addDependent(dependent, "1");

        assertEquals(dependent.getName(), enrollee.getDependents().get(0).getName());
        assertEquals(dependent.getDateOfBirth(), enrollee.getDependents().get(0).getDateOfBirth());
        verify(this.enrollmentDAO, times(1)).save(any(Enrollee.class));

    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void addDependentWithInvalidEnrolleeIdTest() {

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.empty());

        this.dependentService.addDependent(this.createDependent(), "-1");

    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void addDependentWithInvalidDependentIdTest() {

        Enrollee enrollee = this.createEnrollee();
        Dependent dependent = this.createDependent();
        enrollee.getDependents().add(dependent);

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.of(enrollee));

        this.dependentService.addDependent(this.createDependent(), "1");

    }

    @Test
    public void modifyDependentTest() {

        Enrollee enrollee = this.createEnrollee();
        Dependent dependent = this.createDependent();
        enrollee.getDependents().add(dependent);

        Dependent modifiedDependent = this.createDependent();
        modifiedDependent.setName("Junior Test");

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.of(enrollee));

        this.dependentService.modifyDependent(modifiedDependent, "1", "1");

        assertEquals(modifiedDependent.getName(), enrollee.getDependents().get(0).getName());
        verify(this.enrollmentDAO, times(1)).save(any(Enrollee.class));

    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void modifyDependentForInvalidEnrolleeIdTest() {

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.empty());

        this.dependentService.modifyDependent(this.createDependent(), "-1", "1");

    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void modifyDependentWithInvalidDependentIdTest() {

        Enrollee enrollee = this.createEnrollee();
        Dependent dependent = this.createDependent();
        enrollee.getDependents().add(dependent);

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.of(enrollee));

        this.dependentService.modifyDependent(this.createDependent(), "1", "-1");

    }

    @Test
    public void deleteDependentTest() {

        Enrollee enrollee = this.createEnrollee();
        Dependent dependent = this.createDependent();
        enrollee.getDependents().add(dependent);

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.of(enrollee));

        this.dependentService.deleteDependent("1", "1");

        assertEquals(enrollee.getDependents().size(), 0);
        verify(this.enrollmentDAO, times(1)).save(any(Enrollee.class));

    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void deleteDependentForInvalidEnrolleeIdTest() {

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.empty());

        this.dependentService.deleteDependent("-1", "1");

    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void deleteDependentsForInvalidDependentIdTest() {

        Enrollee enrollee = this.createEnrollee();
        Dependent dependent = this.createDependent();
        enrollee.getDependents().add(dependent);

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.of(enrollee));

        this.dependentService.deleteDependent("1", "-1");

    }

    /**
     * Helper Method to create Enrollee objects for tests
     *
     * @return an Enrollee object
     */
    private Enrollee createEnrollee() {

        Enrollee enrollee = new Enrollee();
        enrollee.setId("1");
        enrollee.setName("Test Senior");
        enrollee.setActivationStatus(true);
        enrollee.setDateOfBirth("1900-01-01");
        enrollee.setPhoneNumber("111-222-3333");

        return enrollee;

    }

    /**
     * Helper Method to create Dependent objects for tests
     *
     * @return an Dependent object
     */
    private Dependent createDependent() {

        Dependent dependent = new Dependent();
        dependent.setId("1");
        dependent.setName("Test Junior");
        dependent.setDateOfBirth("1900-01-01");

        return dependent;

    }


}
