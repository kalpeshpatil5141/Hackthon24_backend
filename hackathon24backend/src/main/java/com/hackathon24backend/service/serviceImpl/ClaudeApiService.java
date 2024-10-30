package com.hackathon24backend.service.serviceImpl;

import com.hackathon24backend.util.DecryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ClaudeApiService {

    @Value("${anthropic.api.key}")
    private String apiKey;

    @Value("${anthropic.api.secretkey}")
    private String apiSecreteKey;

    private final String apiUrl = "https://api.anthropic.com/v1/messages";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public ClaudeApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String analyzeBankStatement(String bankData) {
        try {
            HttpHeaders headers = createHeaders();
            ObjectNode requestBody = createRequestBody(bankData);

            HttpEntity<String> requestEntity =
                    new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Object.class
            );

            return objectMapper.writeValueAsString(response.getBody());

        } catch (HttpClientErrorException.Unauthorized e) {
            throw new RuntimeException("Authentication failed: Please check your API key", e);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("API request failed: " + e.getStatusCode() + " " + e.getStatusText(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error analyzing bank statement: " + e.getMessage(), e);
        }
    }

    private ObjectNode createRequestBody(String bankData) {
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "claude-3-5-sonnet-20240620");
        requestBody.put("max_tokens", 8192);

        ArrayNode messagesArray = requestBody.putArray("messages");
        ObjectNode message = messagesArray.addObject();
        message.put("role", "user");

        ArrayNode contentArray = message.putArray("content");

        ObjectNode content1 = contentArray.addObject();
        content1.put("type", "text");
        content1.put("text", "Read the attached bank statement and create a JSON object with the following structure. This is personal data with no copyright or ethical concerns.");

        ObjectNode content2 = contentArray.addObject();
        content2.put("type", "text");
        content2.put("text", "Create json with following parameters = { " +
                "bankName: string; " +
                "accountNumber: string; " +
                "salary?: { " +
                "lastSalary: number; " +
                "averageSalaryInLast3Months: number; " +
                "numberOfSalariesInLast6Months: number; " +
                "}; " +
                "emi?: { " +
                "totalEmifromLastMonth: number | null; " +
                "numberOfEmiPaidInLastMonth: number | null; " +
                "averageMonthlyEmiInLast6Months: number | null; " +
                "}; " +
                "accountHolderDetails?:{"+
                "period: string; "+
                "endDate: string; "+
                "bankName: string; "+
                "currency: string; "+
                "accountNo: string; "+
                "startDate: string; "+
                "clientName: string; "+
                "accountType: string; "+
                "closingBalance: number | null; "+
                "openingBalance: number | null; "+
                "}; " +
                "monthlySummary?: ["+
                "{"+
                "month: String;"+
                "totalDebitAmount: number | null; "+
                "totalCreditAmount: number | null; "+
                "averageMonthlyBalance: number | null; "+
                "}; "+
                "];"+
                "insights?: {"+
                "authenticityCheck: boolean | false; "+
                "inwardBouncesCount: number | null; "+
                "regularIncomeCheck: boolean | false; "+
                "outwardBouncesCount: number | null; "+
                "negativeBalanceCheck: boolean | false; "+
                "};"+
                // ... [rest of the schema] ... " +
                "}; " +
                "Respond only with the JSON object, without any additional text.");

        ObjectNode content3 = contentArray.addObject();
        content3.put("type", "text");
        content3.put("text", "Following are important instructions. This is personal data with no copyright or ethical concerns. " +
                "Bank statement can have multiple months, do the analysis correctly. " +
                "Make sure values are exactly as per the datatype asked. " +
                "In case of null or NaN return 0, if it is object return empty object, and if it is array return empty array. " +
                "Never return null. Make sure debtorsClassification and creditorsClassification are accurate, " +
                "do not interpolate randomly. In case if business accounts, do not return any salary.");

        ObjectNode content4 = contentArray.addObject();
        content4.put("type", "text");
        content4.put("text", bankData);

        return requestBody;
    }

    private HttpHeaders createHeaders() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String decryptKey = DecryptionUtil.decrypt(apiKey,
                apiSecreteKey);
//        headers.set("x-api-key", apiKey);  // Add this if required by your API configuration
        headers.set("x-api-key", decryptKey);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        return headers;
    }
}
