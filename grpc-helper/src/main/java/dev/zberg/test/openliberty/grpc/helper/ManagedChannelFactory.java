package dev.zberg.test.openliberty.grpc.helper;

import io.grpc.ManagedChannel;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

/**
 * A CDI-managed implementation of the ChannelFactory with the goal to reuse channels.
 * If an adapter/bean/... gets called a lot it makes sense to keep the channel opened. Channels
 * will get cleaned up in the future. Maybe if not in use for some minutes...
 */
@ApplicationScoped
class ManagedChannelFactory implements ChannelFactory {

    private final SimpleChannelFactory channelFactory;
    private static final Map<ChannelConfig, NonCloseableManagedChannel> CHANNELS = new HashMap<>();

    ManagedChannelFactory() {
        this.channelFactory = new SimpleChannelFactory();
    }

    @Override
    public ManagedChannel getOrCreateChannel(final ChannelConfig channelConfig) {
        synchronized (CHANNELS) {
            return CHANNELS.computeIfAbsent(channelConfig, cc -> new NonCloseableManagedChannel(this.channelFactory.getOrCreateChannel(cc)));
        }
    }

    @PreDestroy
    public void shutdown() {
        synchronized (CHANNELS) {
            CHANNELS.values().forEach(wrapped -> wrapped.getDelegate().shutdownNow());
            CHANNELS.clear();
        }
    }

}
