I am getting a reference counting error using when consuming SSE with the micronaut provided http client.
This project is to reproduces that issue.

I wrote a simple controller that returns 4 events that are 180 characters each.
There is a test that uses DefaultHttpClient to stream events from this endpoint. 
When the test fails (on the 4th event) with the following stacktrace:

It looks like there is an issue with reference counting? 
Size of the message has something to do with it too, 
because when the messages are less than 160 characters it works fine. 

```bash
Error consuming Server Sent Events: refCnt: 0, decrement: 1
io.micronaut.http.client.exceptions.HttpClientException: Error consuming Server Sent Events: refCnt: 0, decrement: 1
	at io.micronaut.http.client.DefaultHttpClient$4.onError(DefaultHttpClient.java:644)
	at io.reactivex.internal.util.HalfSerializer.onError(HalfSerializer.java:70)
	at io.reactivex.internal.subscribers.StrictSubscriber.onError(StrictSubscriber.java:103)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.lambda$onError$1(InstrumentedSubscriber.java:96)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.onError(InstrumentedSubscriber.java:100)
	at io.reactivex.internal.operators.flowable.FlowableFlatMap$MergeSubscriber.checkTerminate(FlowableFlatMap.java:567)
	at io.reactivex.internal.operators.flowable.FlowableFlatMap$MergeSubscriber.drainLoop(FlowableFlatMap.java:374)
	at io.reactivex.internal.operators.flowable.FlowableFlatMap$MergeSubscriber.drain(FlowableFlatMap.java:366)
	at io.reactivex.internal.operators.flowable.FlowableFlatMap$MergeSubscriber.innerError(FlowableFlatMap.java:606)
	at io.reactivex.internal.operators.flowable.FlowableFlatMap$InnerSubscriber.onError(FlowableFlatMap.java:672)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.lambda$onError$1(InstrumentedSubscriber.java:96)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.onError(InstrumentedSubscriber.java:100)
	at io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapSubscriber.drain(FlowableSwitchMap.java:221)
	at io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber.onError(FlowableSwitchMap.java:403)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.lambda$onError$1(InstrumentedSubscriber.java:96)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.onError(InstrumentedSubscriber.java:100)
	at io.reactivex.internal.subscribers.BasicFuseableSubscriber.onError(BasicFuseableSubscriber.java:101)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.lambda$onError$1(InstrumentedSubscriber.java:96)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.onError(InstrumentedSubscriber.java:100)
	at io.reactivex.internal.subscribers.BasicFuseableSubscriber.onError(BasicFuseableSubscriber.java:101)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.lambda$onError$1(InstrumentedSubscriber.java:96)
	at io.micronaut.reactive.rxjava2.InstrumentedSubscriber.onError(InstrumentedSubscriber.java:100)
	at io.micronaut.http.netty.reactive.HandlerPublisher.exceptionCaught(HandlerPublisher.java:525)
	at io.netty.channel.AbstractChannelHandlerContext.invokeExceptionCaught(AbstractChannelHandlerContext.java:297)
	at io.netty.channel.AbstractChannelHandlerContext.invokeExceptionCaught(AbstractChannelHandlerContext.java:276)
	at io.netty.channel.AbstractChannelHandlerContext.fireExceptionCaught(AbstractChannelHandlerContext.java:268)
	at io.netty.channel.ChannelInboundHandlerAdapter.exceptionCaught(ChannelInboundHandlerAdapter.java:143)
	at io.netty.channel.AbstractChannelHandlerContext.invokeExceptionCaught(AbstractChannelHandlerContext.java:297)
	at io.netty.channel.AbstractChannelHandlerContext.notifyHandlerException(AbstractChannelHandlerContext.java:831)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:376)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:360)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:352)
	at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:102)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:374)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:360)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:352)
	at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:438)
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:326)
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:313)
	at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:427)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:281)
	at io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:253)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:374)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:360)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:352)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1422)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:374)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:360)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:931)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:700)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:635)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:552)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:514)
	at io.netty.util.concurrent.SingleThreadEventExecutor$6.run(SingleThreadEventExecutor.java:1050)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
	at io.netty.util.internal.ReferenceCountUpdater.toLiveRealRefCnt(ReferenceCountUpdater.java:74)
	at io.netty.util.internal.ReferenceCountUpdater.release(ReferenceCountUpdater.java:138)
	at io.netty.buffer.AbstractReferenceCountedByteBuf.release(AbstractReferenceCountedByteBuf.java:100)
	at io.netty.handler.codec.http.DefaultHttpContent.release(DefaultHttpContent.java:94)
	at io.netty.util.ReferenceCountUtil.release(ReferenceCountUtil.java:88)
	at io.netty.channel.SimpleChannelInboundHandler.channelRead(SimpleChannelInboundHandler.java:106)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:374)
	... 28 more
```