package dev.zberg.test.openliberty.grpc.helper;

import io.grpc.ManagedChannel;

public interface ChannelFactory {

    ManagedChannel getOrCreateChannel(final ChannelConfig channelConfig);

    record ChannelConfig(String host, int port, boolean usePlainText) {

    }
}
