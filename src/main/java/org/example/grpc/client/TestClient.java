package org.example.grpc.client;


import io.grpc.*;

import java.util.concurrent.TimeUnit;

public class TestClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private static final String host="127.0.0.1";
    private static final int ip=50051;
    public TestClient(String host,int port){
        //usePlaintext表示明文传输，否则需要配置ssl
        //channel  表示通信通道
        channel= ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        //存根
        blockingStub=GreeterGrpc.newBlockingStub(channel);
    }
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public void  testResult(String name){
        TestRequest request=TestRequest.newBuilder().setName(name).build();
        TestResponse response=blockingStub.testSomeThing(request);
        System.out.println(response.getMessage());
    }
    public static void main(String[] args) {
        TestClient client=new TestClient(host,ip);
        for (int i=0;i<=5;i++){
            client.testResult("<<<<<result>>>>>:"+i);
        }
    }

}
