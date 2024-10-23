package com.hackathon24backend.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon24backend.config.FinvuConfigVariables;
import com.hackathon24backend.config.TokenStorage;
import com.hackathon24backend.payload.FinvuLoginPayload;
import com.hackathon24backend.payload.Header;
import com.hackathon24backend.payload.LoginBody;
import com.hackathon24backend.response.FinvuLoginResponse;
import com.hackathon24backend.service.AccountAggregatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    private final WebClient webClient;
    private final TokenStorage tokenStorage;

    AccountAggregatorImpl(WebClient.Builder webClientBuilder,TokenStorage tokenStorage) {
        this.webClient = webClientBuilder.baseUrl("https://third-party-api.com").build();
        this.tokenStorage = tokenStorage;
    }

    public void callFinvuLoginApi(){

        FinvuConfigVariables finvuConfigVariables = new FinvuConfigVariables();
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

        try {
            System.out.println("Payload: " + new ObjectMapper().writeValueAsString(finvuLoginPayload));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

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

}
