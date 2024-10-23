package com.hackathon24backend.payload.finvupauload;
@lombok.Data
public class Data {
    private String bankId;
    private String id;
    private int caseId;
    private String type;
    private State state;
    private AdharMainState adharState;
    private String status;
    private String name;
    private boolean checked;
    private String applicationRole;
}
