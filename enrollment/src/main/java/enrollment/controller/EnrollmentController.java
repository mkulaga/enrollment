package enrollment.controller;

import enrollment.common.Dependent;
import enrollment.common.Enrollee;
import enrollment.service.DependentService;
import enrollment.service.EnrolleeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Enrollment Application
 * @author Michael Kulaga
 */
@RestController
public class EnrollmentController {

    public final EnrolleeService enrolleeService;
    public final DependentService dependentService;

    /**
     * Controller for EnrollmentController
     * @param enrolleeService the EnrollmentService to use
     * @param dependentService the DependentService to use
     */
    public EnrollmentController(EnrolleeService enrolleeService,
                                DependentService dependentService) {
        this.enrolleeService = enrolleeService;
        this.dependentService = dependentService;
    }

    /**
     * Endpoint to retrieve all the Enrollees in the database
     *
     * @return the list of Enrollee objects
     */
    @GetMapping("/enrollees")
    @ApiResponse(description = "Retrieve all Enrollees")
    public List<Enrollee> getEnrollee() {
        return this.enrolleeService.getEnrollees();
    }

    /**
     * Endpoint to add a new Enrollee
     *
     * @param addEnrollee the new Enrollee to add to the database
     */
    @PostMapping("/enrollees")
    @ApiResponse(description = "Add a new Enrollee")
    public void addNewEnrollee(@RequestBody Enrollee addEnrollee) {

        this.enrolleeService.addEnrollee(addEnrollee);

    }

    /**
     * Endpoint to retrieve the Enrollee by the passed in Id
     *
     * @param enrolleeId the Id of the Enrollee to search for
     * @return the Enrollee with the passed in Id
     */
    @GetMapping("/enrollees/{enrolleeId}")
    @ApiResponse(description = "Retrieve an Enrollee by Id")
    public Enrollee getEnrolleeById(@PathVariable("enrolleeId") String enrolleeId) {
        return this.enrolleeService.getEnrolleeById(enrolleeId);
    }

    /**
     * Endpoint to modify an existing Enrollee
     *
     * @param modifiedEnrollee the modified Enrollee object
     * @param enrolleeId the Enrollee Id to modify
     */
    @PatchMapping("/enrollees/{enrolleeId}")
    @ApiResponse(description = "Modify an existing Enrollee")
    public void modifyEnrollee(@RequestBody Enrollee modifiedEnrollee, @PathVariable("enrolleeId") String enrolleeId) {

        this.enrolleeService.modifyEnrollee(modifiedEnrollee, enrolleeId);

    }

    /**
     * Endpoint to delete an existing Enrollee
     *
     * @param enrolleeId the Enrollee Id to delete
     */
    @DeleteMapping("/enrollees/{enrolleeId}")
    @ApiResponse(description = "Delete an Enrollee")
    public void deleteEnrollee(@PathVariable("enrolleeId") String enrolleeId) {

        this.enrolleeService.deleteEnrollee(enrolleeId);

    }

    /**
     * Endpoint to add a new Dependent to the database
     *
     * @param dependent the Dependent to add
     * @param enrolleeId the Enrollee Id to add the Dependent to
     */
    @PostMapping("/enrollees/{enrolleeId}/dependents")
    @ApiResponse(description = "Add a new Dependent")
    public void addDependents(@RequestBody Dependent dependent, @PathVariable("enrolleeId") String enrolleeId) {

        this.dependentService.addDependent(dependent, enrolleeId);

    }

    /**
     * Endpoint to modify an existing Dependent
     *
     * @param dependent the modified Dependent object
     * @param enrolleeId the Enrollee Id to add the Dependent to
     * @param dependentId the Dependent Id to modify
     */
    @PatchMapping("/enrollees/{enrolleeId}/dependents/{dependentId}")
    @ApiResponse(description = "Modify a Dependent")
    public void modifyDependents(@RequestBody Dependent dependent, @PathVariable("enrolleeId") String enrolleeId, @PathVariable("dependentId") String dependentId) {

        this.dependentService.modifyDependent(dependent, enrolleeId, dependentId);

    }

    /**
     * Endpoint to delete an existing Dependent
     *
     * @param enrolleeId the Enrollee Id to delete the Dependent from
     * @param dependentId the Dependent Id to delete
     */
    @DeleteMapping(value = "/enrollees/{enrolleeId}/dependents/{dependentId}")
    @ApiResponse(description = "Delete a Dependent")
    public void deleteDependents(@PathVariable("enrolleeId") String enrolleeId, @PathVariable("dependentId") String dependentId) {

        this.dependentService.deleteDependent(enrolleeId, dependentId);

    }


}
