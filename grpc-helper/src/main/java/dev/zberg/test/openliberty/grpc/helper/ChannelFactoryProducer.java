package dev.zberg.test.openliberty.grpc.helper;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Producer for ChannelFactory. If we run in a managed context (jee container), the {@link ManagedChannelFactory} will be used
 */
public class ChannelFactoryProducer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleChannelFactory SIMPLE_CHANNEL_FACTORY = new SimpleChannelFactory();

    private ChannelFactoryProducer() {
        //
    }

    public static ChannelFactory getChannelFactory() {
        Optional<ChannelFactory> managedChannelFactory = resolveManagedChannelFactory();
        return managedChannelFactory.orElse(SIMPLE_CHANNEL_FACTORY);
    }

    private static Optional<ChannelFactory> resolveManagedChannelFactory() {
        try {
            final Instance<ChannelFactory> managedChannelFactory = CDI.current().select(ChannelFactory.class);
            if (managedChannelFactory != null && !managedChannelFactory.isUnsatisfied() && !managedChannelFactory.isAmbiguous()) {
                LOGGER.info("ChannelFactory resolved by CDI");
                return Optional.of(managedChannelFactory.get());
            } else {
                LOGGER.info("ChannelFactory by CDI is ether null, unsatisfied or ambiguous: {}", managedChannelFactory);
            }
        } catch (Exception e) {
            // ignore
            LOGGER.info("Unable to resolve ManagedChannelFactory by CDI", e);
        }
        return Optional.empty();
    }

}
