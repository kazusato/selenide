package com.codeborne.selenide.junit;

import java.util.Arrays;
import java.util.logging.Logger;

import org.junit.rules.TestWatcher;

import com.codeborne.selenide.Screenshots;

public class ScrollScreenShooter extends TestWatcher {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	public ScrollScreenShooter setDriversWithScroll(Class<?>... drivers) {
		Screenshots.setDriversWithScroll(drivers);
		Arrays.stream(drivers).forEach(d -> log.info("Driver to take screenshots with scroll: "
				+ d.getClass().getName()));
		
		return this;
	}

}
