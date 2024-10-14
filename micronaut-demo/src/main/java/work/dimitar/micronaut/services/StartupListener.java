package work.dimitar.micronaut.services;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;

@Singleton
public class StartupListener implements ApplicationEventListener<StartupEvent> {

    // JVM start time (captured when the class is loaded)
    private static final long jvmStartTime = System.currentTimeMillis();

    // Time when the application finished starting up
    private long startupDuration;

    public long getStartupDuration() {
        return startupDuration;
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        // Calculate how long it took to start the application
        long startupTime = System.currentTimeMillis();
        startupDuration = startupTime - jvmStartTime;
    }
}
