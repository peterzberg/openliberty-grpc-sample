package dev.zberg.test.openliberty.grpc.helper;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

class SimpleChannelFactory implements ChannelFactory {
    @Override
    public ManagedChannel getOrCreateChannel(final ChannelConfig channelConfig) {
        ManagedChannelBuilder<?> managedChannelBuilder = ManagedChannelBuilder.forAddress(channelConfig.host(), channelConfig.port());
        if (channelConfig.usePlainText()) {
            managedChannelBuilder = managedChannelBuilder.usePlaintext();
        }
        // TODO: apply more configs
        return managedChannelBuilder.build();
    }
}
