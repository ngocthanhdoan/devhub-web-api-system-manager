package com.manager.web.app.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MetricsAnalyzer {

	private static final String PROMETHEUS_ENDPOINT = "http://localhost:8080/actuator/prometheus";

	private ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		try {
			String metricsData = fetchMetrics();
			MetricsSummary summary = parseMetrics(metricsData);
			System.out.println(summary.getSummary());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Map> getSummary() {
		try {
			String metricsData = fetchMetrics();
			MetricsSummary summary = parseMetrics(metricsData);
			return summary.getSummary();
			// return objectMapper.writeValueAsString(listOutPut);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String fetchMetrics() throws IOException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(PROMETHEUS_ENDPOINT);
			try (CloseableHttpResponse response = client.execute(request)) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity);
				}
			}
		}
		return "";
	}

	public static MetricsSummary parseMetrics(String metricsData) {
		MetricsSummary summary = new MetricsSummary();

		String[] lines = metricsData.split("\n");
		for (String line : lines) {
			if (line.startsWith("http_server_requests_seconds_count")) {
				String[] parts = line.split("\\s+");
				if (parts.length == 2) {
					String[] labels = parts[0].split("\\{")[1].split("}")[0].split(",");
					String endpoint = getLabelValue(labels, "uri");
					String method = getLabelValue(labels, "method");
					String status = getLabelValue(labels, "status");
					Double count = Double.parseDouble(parts[1]);
					summary.incrementRequestCount(endpoint, method, status, count);
				}
			} else if (line.startsWith("http_server_requests_seconds_sum")) {
				String[] parts = line.split("\\s+");
				if (parts.length == 2) {
					String[] labels = parts[0].split("\\{")[1].split("}")[0].split(",");
					String endpoint = getLabelValue(labels, "uri");
					String method = getLabelValue(labels, "method");
					Double sum = Double.parseDouble(parts[1]);
					summary.addRequestDurationSum(endpoint, method, sum);
				}
			}
		}
		return summary;
	}

	private static String getLabelValue(String[] labels, String key) {
		for (String label : labels) {
			String[] keyValue = label.split("=");
			if (keyValue[0].equals(key)) {
				return keyValue[1].replace("\"", "");
			}
		}
		return "";
	}
}

class MetricsSummary {
	private ObjectMapper objectMapper = new ObjectMapper();

	private final Map<String, EndpointMetrics> metricsMap = new HashMap<>();

	void incrementRequestCount(String endpoint, String method, String status, Double count) {
		metricsMap.computeIfAbsent(endpoint, k -> new EndpointMetrics(endpoint)).addRequest(method, status, count);
	}

	void addRequestDurationSum(String endpoint, String method, Double sum) {
		metricsMap.computeIfAbsent(endpoint, k -> new EndpointMetrics(endpoint)).addDurationSum(method, sum);
	}

	void printSummary() {
		metricsMap.forEach((endpoint, metrics) -> {
			System.out.println("Endpoint: " + endpoint);
			metrics.getMethods().forEach((method, methodMetrics) -> {
				System.out.println("  Method: " + method);
				System.out.println("    Total Requests: " + methodMetrics.getTotalRequests());
				System.out.println("    Average Latency: " + methodMetrics.getAverageLatency());
				System.out.println("    Success Count: " + methodMetrics.getSuccessCount());
				System.out.println("    Failure Count: " + methodMetrics.getFailureCount());
				System.out.println();
			});
		});
	}

	public List<Map> getSummary() {

		List<Map> listOutPut = new ArrayList<Map>();
		metricsMap.forEach((endpoint, metrics) -> {
			Map<String, Object> endpointMap = new HashMap<String, Object>();
			System.out.println("Endpoint: " + endpoint);
			metrics.getMethods().forEach((method, methodMetrics) -> {
				endpointMap.put("endpoint", endpoint);
				endpointMap.put("total_request", methodMetrics.getTotalRequests());
				endpointMap.put("average_latency", methodMetrics.getAverageLatency());
				endpointMap.put("success_count", methodMetrics.getSuccessCount());
				endpointMap.put("failure_count", methodMetrics.getFailureCount());
				listOutPut.add(endpointMap);
			});
		});

		// Convert metricsMap to JSON string
		return listOutPut;
	}
}

class EndpointMetrics {

	private final String endpoint;

	private final Map<String, MethodMetrics> methodMetricsMap = new HashMap<>();

	EndpointMetrics(String endpoint) {
		this.endpoint = endpoint;
	}

	void addRequest(String method, String status, double count) {
		methodMetricsMap.computeIfAbsent(method, k -> new MethodMetrics(method)).addRequest(status, count);
	}

	void addDurationSum(String method, double sum) {
		methodMetricsMap.computeIfAbsent(method, k -> new MethodMetrics(method)).addDurationSum(sum);
	}

	Map<String, MethodMetrics> getMethods() {
		return methodMetricsMap;
	}
}

class MethodMetrics {

	private final String method;

	private double totalRequests;

	private double successCount;

	private double failureCount;

	private double totalDuration;

	MethodMetrics(String method) {
		this.method = method;
	}

	void addRequest(String status, double count) {
		totalRequests += count;
		if ("200".equals(status)) {
			successCount += count;
		} else {
			failureCount += count;
		}
	}

	void addDurationSum(double sum) {
		totalDuration += sum;
	}

	double getTotalRequests() {
		return totalRequests;
	}

	double getSuccessCount() {
		return successCount;
	}

	double getFailureCount() {
		return failureCount;
	}

	double getAverageLatency() {
		return totalRequests > 0 ? totalDuration / totalRequests : 0;
	}
}
