package io.vepo.cdiexample;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.vepo.cdiexample.logged.Logged;

@ApplicationScoped
public class HiService {
	@Logged
	public void sayHi(@Observes Hello hello) {
		System.out.println("Hi! " + hello.getName());
	}
}
