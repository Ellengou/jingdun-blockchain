package com.yuyoukj.async.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.yuyoukj.mapper.qhd.ConfconfigMapper;

@Service
public class MyAsyncService {
	@Autowired
	private ConfconfigMapper confconfigMapper;

	@Autowired
	@Async
	public void saveLogscoreList() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
