package com.practice.speakup.controllers;

import com.practice.speakup.dtos.ComplainRequest;
import com.practice.speakup.dtos.ComplainResponse;
import com.practice.speakup.handlers.GlobalResponseHandler;
import com.practice.speakup.services.ComplainService;
import com.practice.speakup.services.PDFGenerateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complains")
public class ComplainController {

    @Autowired
    private PDFGenerateService pdfGenerateService;

    @Autowired
    private ComplainService complainService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<GlobalResponseHandler<ComplainResponse>> create(@Valid @RequestBody ComplainRequest request){
        try{
            ComplainResponse response = complainService.createComplain(request);
            return GlobalResponseHandler.successResponse("Complain created successfully", response, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<GlobalResponseHandler<List<ComplainResponse>>> getUserComplains(){
        try{
            List<ComplainResponse> complains = complainService.getUserComplains();
            return GlobalResponseHandler.successResponse("User Complain fetch Successfull", complains, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponseHandler<ComplainResponse>> getUsersComplainById(@PathVariable Long id){
        try{
            ComplainResponse response = complainService.getUserComplainById(id);
            return GlobalResponseHandler.successResponse("Complain fetched successfully", response, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponseHandler<ComplainResponse>> updateComplains(@PathVariable Long id, @RequestBody ComplainRequest request){
        try{
            ComplainResponse response = complainService.updateComplain(id, request);
            return GlobalResponseHandler.successResponse("Complain updated successfully", response, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseHandler<String>> delete(@PathVariable Long id){
        try{
            complainService.deleteComplain(id);
            return GlobalResponseHandler.successResponse("Complain deleted successfully", "Complain Deleted", HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/generate-pdf/{id}")
    public ResponseEntity<byte[]> generatePdfForComplain(@PathVariable Long id){
        try{
            ComplainResponse response = complainService.getUserComplainById(id);

            byte[] pdfContent = pdfGenerateService.generatePdf(response);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=complain_"+ id +".pdf");
            headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
