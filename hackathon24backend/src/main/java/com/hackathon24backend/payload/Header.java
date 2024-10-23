package com.hackathon24backend.payload;

import lombok.*;

@Setter
@Getter
@Builder
public class Header {
    private String rid;
    private String ts;
    private String channelId;

    public Header() {
    }

    public Header(String rid, String ts, String channelId) {
        this.rid = rid;
        this.ts = ts;
        this.channelId = channelId;
    }

}
