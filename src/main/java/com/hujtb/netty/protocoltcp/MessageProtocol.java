package com.hujtb.netty.protocoltcp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class MessageProtocol {
    private int length;
    private byte[] content;
}
