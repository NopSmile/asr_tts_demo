package com.iflytek.vcp.voice.engine.tts.core;

import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 *
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.14.0)",
        comments = "Source: tes.proto")
public final class TESGrpc {

    private TESGrpc() {
    }

    public static final String SERVICE_NAME = "tes.TES";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<Tes.TESRequest,
            Tes.TESResult> getOnNotifyMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "OnNotify",
            requestType = Tes.TESRequest.class,
            responseType = Tes.TESResult.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<Tes.TESRequest,
            Tes.TESResult> getOnNotifyMethod() {
        io.grpc.MethodDescriptor<Tes.TESRequest, Tes.TESResult> getOnNotifyMethod;
        if ((getOnNotifyMethod = TESGrpc.getOnNotifyMethod) == null) {
            synchronized (TESGrpc.class) {
                if ((getOnNotifyMethod = TESGrpc.getOnNotifyMethod) == null) {
                    TESGrpc.getOnNotifyMethod = getOnNotifyMethod =
                            io.grpc.MethodDescriptor.<Tes.TESRequest, Tes.TESResult>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "tes.TES", "OnNotify"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            Tes.TESRequest.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            Tes.TESResult.getDefaultInstance()))
                                    .setSchemaDescriptor(new TESMethodDescriptorSupplier("OnNotify"))
                                    .build();
                }
            }
        }
        return getOnNotifyMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static TESStub newStub(io.grpc.Channel channel) {
        return new TESStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static TESBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        return new TESBlockingStub(channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static TESFutureStub newFutureStub(
            io.grpc.Channel channel) {
        return new TESFutureStub(channel);
    }

    /**
     *
     */
    public static abstract class TESImplBase implements io.grpc.BindableService {

        /**
         *
         */
        public void onNotify(Tes.TESRequest request,
                             io.grpc.stub.StreamObserver<Tes.TESResult> responseObserver) {
            asyncUnimplementedUnaryCall(getOnNotifyMethod(), responseObserver);
        }

        @Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getOnNotifyMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            Tes.TESRequest,
                                            Tes.TESResult>(
                                            this, METHODID_ON_NOTIFY)))
                    .build();
        }
    }

    /**
     *
     */
    public static final class TESStub extends io.grpc.stub.AbstractStub<TESStub> {
        private TESStub(io.grpc.Channel channel) {
            super(channel);
        }

        private TESStub(io.grpc.Channel channel,
                        io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected TESStub build(io.grpc.Channel channel,
                                io.grpc.CallOptions callOptions) {
            return new TESStub(channel, callOptions);
        }

        /**
         *
         */
        public void onNotify(Tes.TESRequest request,
                             io.grpc.stub.StreamObserver<Tes.TESResult> responseObserver) {
            ClientCalls.asyncUnaryCall(
                    getChannel().newCall(getOnNotifyMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     *
     */
    public static final class TESBlockingStub extends io.grpc.stub.AbstractStub<TESBlockingStub> {
        private TESBlockingStub(io.grpc.Channel channel) {
            super(channel);
        }

        private TESBlockingStub(io.grpc.Channel channel,
                                io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected TESBlockingStub build(io.grpc.Channel channel,
                                        io.grpc.CallOptions callOptions) {
            return new TESBlockingStub(channel, callOptions);
        }

        /**
         *
         */
        public Tes.TESResult onNotify(Tes.TESRequest request) {
            return blockingUnaryCall(
                    getChannel(), getOnNotifyMethod(), getCallOptions(), request);
        }
    }

    /**
     *
     */
    public static final class TESFutureStub extends io.grpc.stub.AbstractStub<TESFutureStub> {
        private TESFutureStub(io.grpc.Channel channel) {
            super(channel);
        }

        private TESFutureStub(io.grpc.Channel channel,
                              io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected TESFutureStub build(io.grpc.Channel channel,
                                      io.grpc.CallOptions callOptions) {
            return new TESFutureStub(channel, callOptions);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<Tes.TESResult> onNotify(
                Tes.TESRequest request) {
            return futureUnaryCall(
                    getChannel().newCall(getOnNotifyMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_ON_NOTIFY = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final TESImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(TESImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_ON_NOTIFY:
                    serviceImpl.onNotify((Tes.TESRequest) request,
                            (io.grpc.stub.StreamObserver<Tes.TESResult>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class TESBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        TESBaseDescriptorSupplier() {
        }

        @Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return Tes.getDescriptor();
        }

        @Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("TES");
        }
    }

    private static final class TESFileDescriptorSupplier
            extends TESBaseDescriptorSupplier {
        TESFileDescriptorSupplier() {
        }
    }

    private static final class TESMethodDescriptorSupplier
            extends TESBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        TESMethodDescriptorSupplier(String methodName) {
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
            synchronized (TESGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new TESFileDescriptorSupplier())
                            .addMethod(getOnNotifyMethod())
                            .build();
                }
            }
        }
        return result;
    }
}
