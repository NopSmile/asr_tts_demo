package com.iflytek.tts.sdk.core;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 *
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.14.0)",
        comments = "Source: Tts.proto")
public final class TtsGrpc {

    private TtsGrpc() {
    }

    public static final String SERVICE_NAME = "tts.Tts";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest,
            com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult> getCreateRecMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "createRec",
            requestType = com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest.class,
            responseType = com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult.class,
            methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
    public static io.grpc.MethodDescriptor<com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest,
            com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult> getCreateRecMethod() {
        io.grpc.MethodDescriptor<com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest, com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult> getCreateRecMethod;
        if ((getCreateRecMethod = TtsGrpc.getCreateRecMethod) == null) {
            synchronized (TtsGrpc.class) {
                if ((getCreateRecMethod = TtsGrpc.getCreateRecMethod) == null) {
                    TtsGrpc.getCreateRecMethod = getCreateRecMethod =
                            io.grpc.MethodDescriptor.<com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest, com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
                                    .setFullMethodName(generateFullMethodName(
                                            "tts.Tts", "createRec"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult.getDefaultInstance()))
                                    .setSchemaDescriptor(new TtsMethodDescriptorSupplier("createRec"))
                                    .build();
                }
            }
        }
        return getCreateRecMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static TtsStub newStub(io.grpc.Channel channel) {
        return new TtsStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static TtsBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        return new TtsBlockingStub(channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static TtsFutureStub newFutureStub(
            io.grpc.Channel channel) {
        return new TtsFutureStub(channel);
    }

    /**
     *
     */
    public static abstract class TtsImplBase implements io.grpc.BindableService {

        /**
         *
         */
        public io.grpc.stub.StreamObserver<com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest> createRec(
                io.grpc.stub.StreamObserver<com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult> responseObserver) {
            return asyncUnimplementedStreamingCall(getCreateRecMethod(), responseObserver);
        }

        @Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getCreateRecMethod(),
                            asyncBidiStreamingCall(
                                    new MethodHandlers<
                                            com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest,
                                            com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult>(
                                            this, METHODID_CREATE_REC)))
                    .build();
        }
    }

    /**
     *
     */
    public static final class TtsStub extends io.grpc.stub.AbstractStub<TtsStub> {
        private TtsStub(io.grpc.Channel channel) {
            super(channel);
        }

        private TtsStub(io.grpc.Channel channel,
                        io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected TtsStub build(io.grpc.Channel channel,
                                io.grpc.CallOptions callOptions) {
            return new TtsStub(channel, callOptions);
        }

        /**
         *
         */
        public io.grpc.stub.StreamObserver<com.iflytek.tts.sdk.core.TtsOuterClass.TtsRequest> createRec(
                io.grpc.stub.StreamObserver<com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult> responseObserver) {
            return asyncBidiStreamingCall(
                    getChannel().newCall(getCreateRecMethod(), getCallOptions()), responseObserver);
        }
    }

    /**
     *
     */
    public static final class TtsBlockingStub extends io.grpc.stub.AbstractStub<TtsBlockingStub> {
        private TtsBlockingStub(io.grpc.Channel channel) {
            super(channel);
        }

        private TtsBlockingStub(io.grpc.Channel channel,
                                io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected TtsBlockingStub build(io.grpc.Channel channel,
                                        io.grpc.CallOptions callOptions) {
            return new TtsBlockingStub(channel, callOptions);
        }
    }

    /**
     *
     */
    public static final class TtsFutureStub extends io.grpc.stub.AbstractStub<TtsFutureStub> {
        private TtsFutureStub(io.grpc.Channel channel) {
            super(channel);
        }

        private TtsFutureStub(io.grpc.Channel channel,
                              io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected TtsFutureStub build(io.grpc.Channel channel,
                                      io.grpc.CallOptions callOptions) {
            return new TtsFutureStub(channel, callOptions);
        }
    }

    private static final int METHODID_CREATE_REC = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final TtsImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(TtsImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_CREATE_REC:
                    return (io.grpc.stub.StreamObserver<Req>) serviceImpl.createRec(
                            (io.grpc.stub.StreamObserver<com.iflytek.tts.sdk.core.TtsOuterClass.TtsResult>) responseObserver);
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class TtsBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        TtsBaseDescriptorSupplier() {
        }

        @Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return com.iflytek.tts.sdk.core.TtsOuterClass.getDescriptor();
        }

        @Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("Tts");
        }
    }

    private static final class TtsFileDescriptorSupplier
            extends TtsBaseDescriptorSupplier {
        TtsFileDescriptorSupplier() {
        }
    }

    private static final class TtsMethodDescriptorSupplier
            extends TtsBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        TtsMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (TtsGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new TtsFileDescriptorSupplier())
                            .addMethod(getCreateRecMethod())
                            .build();
                }
            }
        }
        return result;
    }
}
