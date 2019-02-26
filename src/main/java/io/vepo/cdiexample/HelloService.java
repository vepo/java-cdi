package io.vepo.cdiexample;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import io.vepo.cdiexample.logged.Logged;

@ApplicationScoped
public class HelloService {

	private AtomicInteger counter = new AtomicInteger(0);

	@Inject
	private Event<Hello> provider;

	@Logged
	public void sayHello() {
		System.out.println("Hello!");
		provider.fire(new Hello("Person " + counter.incrementAndGet()));
	}

}
