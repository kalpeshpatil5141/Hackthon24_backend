package com.hackathon24backend.controller;

import com.hackathon24backend.payload.KycPayload;
import com.hackathon24backend.payload.finvupauload.*;
import com.hackathon24backend.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/kyc")
@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
public class KycController {


    @PostMapping("/pannumber")
    public ResponseEntity<?> validatePanNumber(@RequestBody KycPayload request) {

        PanState panState = new PanState();
        panState.setAddress("RUPNAGAR, E-19 DAPOLI S.O DAPOLI MAHARASHTRA 415712");
        panState.setGender("MALE");
        panState.setAadhaarLinked("true");
        panState.setBirthDate("1996-12-07");
        panState.setFullName("MAHESH TANAJI SAGARE");
        panState.setMaskedAadhaar("XXXXXXXX5259");
        panState.setPanNumber("GEMPS4136F");

        State state = new State();
        state.setPan(panState);

        Data data = new Data();
        data.setBankId("0f37e2da-b45f-4ffe-a88e-732edccfa3ec");
        data.setId("ad301d54-8412-48ea-9552-05c2e87fa13a");
        data.setCaseId(1);
        data.setType("pan");
        data.setState(state);
        data.setStatus("inProgress");
        data.setName("PAN");
        data.setChecked(false);
        data.setApplicationRole("Applicant");

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Found");
        responseMessage.setData(data);

        return ResponseEntity.ok(new ApiResponse(true,"Pan Number Verified Successfully",responseMessage));
    }

    @PostMapping("/adhaar")
    public ResponseEntity<?> validateAdhar(@RequestBody KycPayload request) {
        AddhaarState adharState = new AddhaarState();
        adharState.setAddress("Anur, Kagal, Kolhapur, Maharashtra, Anur, 416235, India");
        adharState.setGender("MALE");
        adharState.setAdhaarNumber("250456065026");
        adharState.setBirthDate("1995-04-01");
        adharState.setFullName("Kirankumar Balkrishna Bhole");
        adharState.setProfileImage("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");

        AdharMainState state = new AdharMainState();
        state.setAadhar(adharState);

        Data data = new Data();
        data.setBankId("0f37e2da-b45f-4ffe-a88e-732edccfa3ec");
        data.setId("0aab8140-23ea-4ed2-8ea0-1c8d08e56cb4");
        data.setCaseId(1);
        data.setType("aadhar");
        data.setAdharState(state);
        data.setStatus("inProgress");
        data.setName("AADHAAR");
        data.setChecked(false);
        data.setApplicationRole("Applicant");

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Found");
        responseMessage.setData(data);

        return ResponseEntity.ok(new ApiResponse(true,"Adhar Number Verified Successfully",responseMessage));
    }
}
