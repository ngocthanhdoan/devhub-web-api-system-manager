package com.manager.web.app.analyzer.trx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manager.web.app.analyzer.MetricsAnalyzer;
import com.manager.web.app.plugins.returnObject;

import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/analyzer")
public class AnalyzerController {
	@Autowired
	private MeterRegistry meterRegistry;

	private returnObject responseObject;

	public AnalyzerController() {
		// TODO Auto-generated constructor stub
		responseObject = new returnObject();
		responseObject.setReturnCode(0);
		responseObject.setMsgDescs("Success");
	}

	@PostMapping("/reset")
	public returnObject resetMetrics() {
		meterRegistry.clear();
		return responseObject;
	}

	private static final Logger logger = LoggerFactory.getLogger(AnalyzerController.class);

	@GetMapping("/summary")
	@ResponseBody
	public returnObject getSummary() {
		long startTime = System.currentTimeMillis();
		try {
			MetricsAnalyzer analyzer = new MetricsAnalyzer();
			responseObject.setReturnData(analyzer.getSummary());
			return responseObject;
		} catch (Exception e) {
			// logger.error("Error handling request to /api/endpoint", e);
			responseObject.setReturnCode(-1);
			return responseObject;
		}
	}
}