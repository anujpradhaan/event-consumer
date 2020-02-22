package com.eventchase.consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping(value = "/health")
public class HealthController {

	@GetMapping
	public String checkHealthStatus() throws UnknownHostException {
		String ip = InetAddress.getLocalHost().getHostAddress();
		return "Consumer Working on IP : " + ip;
	}
}
