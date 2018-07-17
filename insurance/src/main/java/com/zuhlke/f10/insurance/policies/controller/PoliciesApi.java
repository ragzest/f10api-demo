/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.zuhlke.f10.insurance.policies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhlke.f10.insurance.model.GenericError;
import com.zuhlke.f10.insurance.model.PolicyDetails;
import com.zuhlke.f10.insurance.model.ServerError;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-16T17:46:09.699+08:00")

@Api(value = "policies", description = "the policies API")
public interface PoliciesApi {

    Logger log = LoggerFactory.getLogger(PoliciesApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Get Policy Details", nickname = "gETPoliciesPolicyId", notes = "", response = PolicyDetails.class, authorizations = {
        @Authorization(value = "Authorization"),
        @Authorization(value = "X-API-Key")
    }, tags={ "Policies", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful Response", response = PolicyDetails.class),
        @ApiResponse(code = 400, message = "Bad Request", response = GenericError.class),
        @ApiResponse(code = 401, message = "Authentication Error", response = GenericError.class),
        @ApiResponse(code = 403, message = "Authorization Failed", response = GenericError.class),
        @ApiResponse(code = 404, message = "Not Found", response = GenericError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ServerError.class) })
    @RequestMapping(value = "/policies/{policyId}",
        method = RequestMethod.GET)
    ResponseEntity<PolicyDetails> getPolicy(@ApiParam(value = "", required = true) @PathVariable("policyId") String policyId);

}
