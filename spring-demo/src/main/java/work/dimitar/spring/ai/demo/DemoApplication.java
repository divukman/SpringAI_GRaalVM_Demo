package work.dimitar.spring.ai.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@SpringBootApplication
public class DemoApplication {
	private static Instant startTime;
	private static Instant readyTime;

	public static void main(String[] args) {
		startTime = Instant.now();
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "Time between start and ApplicationReadyEvent: "
				+ Duration.between(startTime, readyTime).toMillis()
				+ "ms";
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		readyTime = Instant.now();
	}
}
