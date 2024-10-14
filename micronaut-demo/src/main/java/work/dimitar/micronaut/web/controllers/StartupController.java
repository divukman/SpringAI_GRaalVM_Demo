package work.dimitar.micronaut.web.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import work.dimitar.micronaut.services.StartupListener;


@Controller("/")
public class StartupController {

    @Inject
    private StartupListener startupListener;

    @Get("/")
    public String getStartupDuration() {
        // Get the startup duration in milliseconds
        long startupDurationMillis = startupListener.getStartupDuration();

        // Return the startup time in seconds
        return "Application startup took " + startupDurationMillis + "ms";
    }
}
