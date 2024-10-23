package com.hackathon24backend.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon24backend.config.TokenStorage;
import com.hackathon24backend.payload.FinvuLoginPayload;
import com.hackathon24backend.payload.Header;
import com.hackathon24backend.payload.LoginBody;
import com.hackathon24backend.payload.finvupauload.*;
import com.hackathon24backend.response.ConsentStatusResponse;
import com.hackathon24backend.response.FiResponse;
import com.hackathon24backend.response.FinvuConsentPlusResponse;
import com.hackathon24backend.response.FinvuLoginResponse;
import com.hackathon24backend.service.AccountAggregatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AccountAggregatorImpl implements AccountAggregatorService {
    @Value("${app.rid}")
    private String rid;

    @Value("${app.ts}")
    private String ts;

    @Value("${app.channelId}")
    private String channelId;

    @Value("${app.userId}")
    private String userId;

    @Value("${app.password}")
    private String password;

    @Value("${app.loginuri}")
    private String loginuri;

    @Value("${app.baseurl}")
    private String baseurl;

    private final WebClient webClient;
    private final TokenStorage tokenStorage;

    AccountAggregatorImpl(WebClient.Builder webClientBuilder,TokenStorage tokenStorage) {
        this.webClient = webClientBuilder.baseUrl("https://third-party-api.com").build();
        this.tokenStorage = tokenStorage;
    }

    public void callFinvuLoginApi(){

       Header header = Header.builder()
                .rid(this.rid)
                .ts(this.ts)
                .channelId(this.channelId)
               .build();
        LoginBody loginBody = LoginBody.builder()
                .userId(this.userId)
                .password(this.password)
                .build();
        FinvuLoginPayload finvuLoginPayload = new FinvuLoginPayload();
        finvuLoginPayload.setHeader(header);
        finvuLoginPayload.setBody(loginBody);

//        try {
//            System.out.println("Payload: " + new ObjectMapper().writeValueAsString(finvuLoginPayload));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

        Mono<FinvuLoginResponse> stringMono = webClient.post()
                .uri(loginuri)
                .header("Content-Type", "application/json")
                // Add Authorization header if needed
                // .header("Authorization", "Bearer your_token")
                .bodyValue(finvuLoginPayload)
                .retrieve()
                .bodyToMono(FinvuLoginResponse.class);
        AtomicReference<String> token = new AtomicReference<>("");
        stringMono.subscribe(response -> {
            System.out.println("Response: " + response.getBody().getToken());
            tokenStorage.storeToken("token",response.getBody().getToken());
            token.set(response.getBody().getToken());

        }, error -> {
            // Handle the error case
            System.err.println("Error occurred: " + error.getMessage());
        });
//        System.out.println("Response:"+response);
//        return token;
    }

    @Override
    public FinvuConsentPlusResponse consentRquestPlus(String mobileNumber) {

        Header header = Header.builder()
                .rid("1100000000289243")
                .ts("2023-02-08T12:24:01.890+0530")
                .channelId(this.channelId)
                .build();
        ConsentPlus consentPlus = new ConsentPlus();

        consentPlus.setCustId(mobileNumber+"@finvu");
        consentPlus.setConsentDescription("Personal finance management");
        consentPlus.setAaId("cookiejar-aa@finvu.in");
        consentPlus.setTemplateName("FINVUDEMO_TESTING");
        consentPlus.setUserSessionId("sessionid123");
        consentPlus.setRedirectUrl("https://dev.credit-os.mettarev.com/loan_officer/case/27");
        consentPlus.setFip(new ArrayList<>());
        consentPlus.setConsentDetails(new ConsentDetails());

        ConsentPayload consentPayload = new ConsentPayload();
        consentPayload.setBody(consentPlus);
        consentPayload.setHeader(header);
        String token = tokenStorage.getToken("token");
        tokenStorage.storeToken("custId", mobileNumber+"@finvu");
        if(token != null && !token.isEmpty()) {

            try {
                System.out.println("Payload: " + new ObjectMapper().writeValueAsString(consentPayload));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

//            Mono<FinvuConsentPlusResponse> stringMono = webClient.post()
//                    .uri(baseurl + "/ConsentRequestPlus")
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer "+token)
//                    .bodyValue(consentPayload)
//                    .retrieve()
//                    .bodyToMono(FinvuConsentPlusResponse.class);
//
//            stringMono.subscribe(response -> {
//                tokenStorage.storeToken("consentHandle",response.getBody().getConsentHandle());
//                System.out.println("Consent Handle Value: "+response.getBody().getConsentHandle());
//            }, error -> {
//                // Handle the error case
//                System.err.println("Error occurred: " + error.getMessage());
//            });





            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = null;
            try {
                requestBody = objectMapper.writeValueAsString(consentPayload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            HttpClient client2 = HttpClient.newHttpClient();
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create(baseurl + "/ConsentRequestPlus"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token.trim())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response2 = null;
            try {
                response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            String responseBody = response2.body();

            try {
                FinvuConsentPlusResponse finvuConsentPlusResponse = objectMapper.readValue(responseBody, FinvuConsentPlusResponse.class);
                tokenStorage.storeToken("consentHandle",finvuConsentPlusResponse.getBody().getConsentHandle());
                System.out.println("Consent Handle Value: "+finvuConsentPlusResponse.getBody().getConsentHandle());
                return finvuConsentPlusResponse;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return  null;
    }

    @Override
    public void checkConsentStatus() throws IOException, InterruptedException {

        String consentHandle = tokenStorage.getToken("consentHandle");
        String custID = tokenStorage.getToken("custId");
        String token = tokenStorage.getToken("token");

        System.out.println("consentHandle"+consentHandle);
        System.out.println("custID"+custID);
        System.out.println("token"+token);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseurl + "/ConsentStatus/" + consentHandle + "/" + custID))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token.trim())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println(responseBody);

// Optionally deserialize the response to ConsentStatusResponse using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        ConsentStatusResponse consentStatus = objectMapper.readValue(responseBody, ConsentStatusResponse.class);

        if(consentStatus != null) {
            if (consentStatus.getBody().getConsentStatus().equalsIgnoreCase("REQUESTED")) {
                System.out.println("Rejected");
            } else {
                tokenStorage.storeToken("consentId",consentStatus.getBody().getConsentId());
                System.out.println("Accepted");

                Header header = Header.builder()
                        .rid("2019-12-02T09:40:09.804+0000")
                        .ts("2019-12-02T09:40:09.804+0000")
                        .channelId(this.channelId)
                        .build();

                String consentId = consentStatus.getBody().getConsentId();

                FiRequestBody fiRequestBody = new FiRequestBody();
                fiRequestBody.setConsentId(consentStatus.getBody().getConsentId());
                fiRequestBody.setConsentHandleId(consentHandle);
                fiRequestBody.setCustId(custID);
                fiRequestBody.setDateTimeRangeTo(LocalDateTime.now().toString());
                fiRequestBody.setDateTimeRangeFrom(LocalDateTime.now().minusYears(1).toString());

                FiRequestPayload fiRequestPayload = new FiRequestPayload();
                fiRequestPayload.setHeader(header);
                fiRequestPayload.setBody(fiRequestBody);

                String requestBody = objectMapper.writeValueAsString(fiRequestPayload);

                HttpClient client2 = HttpClient.newHttpClient();
                HttpRequest request2 = HttpRequest.newBuilder()
                        .uri(URI.create(baseurl + "/FIRequest"))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token.trim())
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                String responseBody2 = response2.body();
                FiResponse fiResponse = objectMapper.readValue(responseBody2, FiResponse.class);
                if(fiResponse != null) {
                    String sessionId = fiResponse.getBody().getSessionId();
                    tokenStorage.storeToken("sessionId", fiResponse.getBody().getSessionId());
                    System.out.println("@@@@@@@@@@@@@@@@@@@ " + fiResponse.getBody().getSessionId());

                    HttpClient client3 = HttpClient.newHttpClient();
                    HttpRequest request3 = HttpRequest.newBuilder()
                            .uri(URI.create(baseurl + "/FIStatus/"+consentId+"/" +sessionId+"/"+ consentHandle + "/" + custID))
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Bearer " + token.trim())
                            .build();

                    HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
                    String responseBody3 = response3.body();
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!"+responseBody3);
                }
            }
        }
    }
}
