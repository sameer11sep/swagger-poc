package com.egencia.sandbox.controller;

import com.egencia.sandbox.model.*;
import com.egencia.sandbox.service.AuthenticationTokenService;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by samarora on 9/12/14.
 */
@Api(value = "ping", description = "Retrieves the version of the Application")
@Controller
public class IndexController {

    @Autowired
    AuthenticationTokenService authenticationTokenService;

    @ApiOperation(value = "Returns the version and build information for the Application" )
    @RequestMapping(value = "/v1/ping", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Version ping() {
        return new Version("Egencia Sandbox", "1.0.0");
    }


    @RequestMapping(value = "/v1/requestResetPassword",method = RequestMethod.POST
            ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResetPasswordResponse sendResetLink(@RequestBody GeneratePasswordLinkRequest request){
        String url = authenticationTokenService.generateToken(request.getUserId(),request.getCompanyId(),request.getProductId(),request.getExpiration());
        return new ResetPasswordResponse("Successfully generated url to reset password",url);
    }

    @RequestMapping(value = "/v1/resetPassword/{token}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TokenValidationResponse validateToken(@PathVariable String token){
        if(token.equals("exception")){
            throw new IllegalArgumentException("Exception Occured");
        }
        return authenticationTokenService.validateToken(token);
    }


    @RequestMapping(value = "/v1/test/patch",method = RequestMethod.PATCH,consumes = "application/json-patch")
    public String testPatch(){
        throw new IllegalArgumentException("Exception Occured");
    }


}
