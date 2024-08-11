package com.manager.web.app.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MetricsLogger {

    @Autowired
    private MeterRegistry meterRegistry;

    public void logHttpMetrics(String uri, String method, String status, long duration, boolean success) {
        String outcome = success ? "SUCCESS" : "CLIENT_ERROR";

        Timer timer = meterRegistry.find("http_server_requests_seconds").timer();

        if (timer != null) {
            // Update counts
            Timer.Sample sample = Timer.start(meterRegistry);
            sample.stop(timer);
        }

        System.out.printf("http_server_requests_seconds_count{application=\"myapp\",exception=\"None\",method=\"%s\",outcome=\"%s\",status=\"%s\",uri=\"%s\",} ", method, outcome, status, uri);
        System.out.printf("http_server_requests_seconds_sum{application=\"myapp\",exception=\"None\",method=\"%s\",outcome=\"%s\",status=\"%s\",uri=\"%s\",} ", method, outcome, status, uri);
        System.out.printf("http_server_requests_seconds_max{application=\"myapp\",exception=\"None\",method=\"%s\",outcome=\"%s\",status=\"%s\",uri=\"%s\",} ", method, outcome, status, uri);
        meterRegistry.timer("http_server_requests_seconds_count", "uri", uri, "method", method, "status", status, "outcome", outcome).record(duration, TimeUnit.NANOSECONDS);
        meterRegistry.timer("http_server_requests_seconds_sum", "uri", uri, "method", method, "status", status, "outcome", outcome).record(duration, TimeUnit.NANOSECONDS);
        meterRegistry.timer("http_server_requests_seconds_max", "uri", uri, "method", method, "status", status, "outcome", outcome).record(duration, TimeUnit.NANOSECONDS);
    }
}
