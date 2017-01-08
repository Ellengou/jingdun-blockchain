package com.yuyoukj.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MyAsyncService {

	@Async
	public void test() {
		try {
			Thread.sleep(1000);
			//

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
