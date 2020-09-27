package enrollment.service;

import enrollment.common.Enrollee;
import enrollment.exceptions.ResourceDoesNotExistException;
import enrollment.repository.EnrollmentDAO;
import enrollment.validation.EnrollmentValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for EnrolleeService class
 * @author Michael Kulaga
 */
public class EnrolleeServiceTest {

    @Mock
    private EnrollmentDAO enrollmentDAO;

    @Mock
    private EnrollmentValidator enrollmentValidator;

    private EnrolleeService enrolleeService;

    /**
     * Setting up mocks
     */
    @Before
    public void setUp() {
        initMocks(this);

        this.enrolleeService = new EnrolleeService(enrollmentDAO, enrollmentValidator);
    }

    @Test
    public void getEnrolleesTest() {

        Enrollee enrollee1 = this.createEnrollee();
        Enrollee enrollee2 = this.createEnrollee();
        enrollee2.setId("2");

        List<Enrollee> enrolleeList = new ArrayList<>(Arrays.asList(enrollee1, enrollee2));

        given(enrollmentDAO.findAll()).willReturn(enrolleeList);

        List<Enrollee> actualList = this.enrolleeService.getEnrollees();

        assertEquals(enrolleeList.size(), actualList.size());

    }

    @Test
    public void getEnrolleeByIdTest() {

        Enrollee expected = this.createEnrollee();

        given(enrollmentDAO.findById(anyString())).willReturn(Optional.of(expected));

        Enrollee actual = this.enrolleeService.getEnrolleeById("1");

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void getEnrolleeByInvalidIdTest() {

        given(enrollmentDAO.findById(anyString())).willReturn(Optional.empty());

        this.enrolleeService.getEnrolleeById("-1");
    }

    @Test
    public void addEnrolleeTest() {

        this.enrolleeService.addEnrollee(this.createEnrollee());

        verify(this.enrollmentDAO, times(1)).insert(any(Enrollee.class));
    }

    @Test
    public void modifyEnrolleeTest() {

        Enrollee enrollee = this.createEnrollee();

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.of(enrollee));

        this.enrolleeService.modifyEnrollee(enrollee, "1");

        verify(this.enrollmentDAO, times(1)).save(any(Enrollee.class));
    }

    @Test(expected = ResourceDoesNotExistException.class)
    public void modifyEnrolleeWithInvalidIdTest() {

        given(this.enrollmentDAO.findById(anyString())).willReturn(Optional.empty());

        this.enrolleeService.modifyEnrollee(this.createEnrollee(), "1");
    }

    @Test
    public void deleteEnrolleeTest() {

        this.enrolleeService.deleteEnrollee("1");

        verify(this.enrollmentDAO, times(1)).deleteById(anyString());
    }

    /**
     * Helper Method to create Enrollee objects for tests
     *
     * @return an Enrollee object
     */
    private Enrollee createEnrollee() {

        Enrollee enrollee = new Enrollee();
        enrollee.setId("1");
        enrollee.setName("Test Testing");
        enrollee.setActivationStatus(true);
        enrollee.setDateOfBirth("1900-01-01");
        enrollee.setPhoneNumber("111-222-3333");

        return enrollee;

    }


}
