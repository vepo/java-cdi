package io.vepo.cdiexample;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.BeforeDestroyed;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CDIApplication {
	private static final Logger logger = LoggerFactory.getLogger(CDIApplication.class);

	private AtomicBoolean running;

	@Inject
	private HelloService helloService;

	@PostConstruct
	void init() {
		logger.info("Creating CDI Application!");
		this.running = new AtomicBoolean(true);
	}

	public void containerInitialied(@Observes @Initialized(ApplicationScoped.class) Object event) {
		logger.info("Container Initialized! event={}", event);
	}

	public void shutdownRequested(@Observes @BeforeDestroyed(ApplicationScoped.class) Object event) {
		logger.info("Shutdown requested! event={}", event);
		this.running.set(false);
	}

	public void shutdown(@Observes @Destroyed(ApplicationScoped.class) Object event) {
		logger.info("Container destroyed! event={}", event);
	}

	public static void main(String[] args) {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			container.select(CDIApplication.class).get().run();
		}
	}

	public void run() {
		logger.info("Starting CDIApplication...");
		while (this.running.get()) {
			helloService.sayHello();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.info("Thread interrupted!");
				Thread.currentThread().interrupt();
			}
		}
		logger.info("CDI Application finished!");
	}
}