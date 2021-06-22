package com.hujtb.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 对空闲检测进一步处理的handler
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventState = null;
            switch (event.state()) {
                case ALL_IDLE:
                    eventState = "读空闲";
                    break;
                case READER_IDLE:
                    eventState = "写空闲";
                    break;
                case WRITER_IDLE:
                    eventState = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "--超时时间--" + eventState);
        }
    }
}
