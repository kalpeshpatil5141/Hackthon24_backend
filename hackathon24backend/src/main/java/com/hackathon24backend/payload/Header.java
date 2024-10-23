package com.hackathon24backend.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Header {
   private String rid;
    private String ts;
    private String channelId;
}
