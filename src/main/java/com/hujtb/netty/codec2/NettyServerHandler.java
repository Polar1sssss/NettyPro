package com.hujtb.netty.codec2;

import com.hujtb.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个Handler，需要继承netty规定好的某个HandlerAdapter（规范），才能有相关的功能
 *
 * @Author: hujtb
 * @Date: 2020/11/15 11:49
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    /**
     * 通道读取数据触发事件
     *
     * @param ctx：上下文对象，含有管道pipeline、通道channel
     * @param msg：客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        // 根据DataType显示不同信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("id -- " + student.getId() + ", name -- " + student.getName());
        } else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("name -- " + worker.getName() + ", age -- " + worker.getAge());
        } else {
            System.out.println("传输类型不正确");
        }
    }

    /**
     * 数据读取完毕触发事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        // 对发送数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端, miao1", CharsetUtil.UTF_8));
    }

    /**
     * 通道发生异常触发事件
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}