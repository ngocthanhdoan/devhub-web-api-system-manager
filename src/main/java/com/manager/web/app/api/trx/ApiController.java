package com.manager.web.app.api.trx;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manager.web.app.abs.AbstractAnnotatedClass;
import com.manager.web.app.abs.DataSet;
import com.manager.web.app.annotation.ParamName;
import com.manager.web.app.api.repository.DevhubApiCodeMapRepository;
import com.manager.web.app.api.repository.DevhubApiMappingRepository;
import com.manager.web.app.api.vo.DevhubApiCodeMap;
import com.manager.web.app.api.vo.DevhubApiMapping;
import com.manager.web.app.config.MetricsLogger;
import com.manager.web.app.plugins.returnObject;

@RestController
@RequestMapping("/api")
public class ApiController {
	public String myMethod1(@ParamName("username") String user, @ParamName("password") int pass) {

		return " out put:Username: " + user + ", Password: " + pass;
	}

	@Autowired
	private DevhubApiMappingRepository apiMappingRepository;
	@Autowired
	private DevhubApiCodeMapRepository apiCodeMapRepository;
	@Autowired
	private MetricsLogger metricsLogger;
    private final DataSet dataSet;

    @Autowired
    public ApiController(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
	@RequestMapping("/**")
	public returnObject handleDynamicRequest(jakarta.servlet.http.HttpServletRequest request,
			@RequestParam Map<String, Object> queryParameters) {
		returnObject responseObject = new returnObject();
		long startTime = System.currentTimeMillis();
		String requestUri = request.getRequestURI();
		try {
			System.out.println(requestUri);
			// Retrieve API mapping details from the database
			DevhubApiMapping mapping = apiMappingRepository.findById(requestUri).orElse(null);
			if (mapping != null) {
				Object result = null;
				String callType = mapping.getCallType(); // Assuming `getCallType` method returns the type of call

				if ("sql_query".equals(callType)) {
					// Use DataSet to execute SQL query
					DevhubApiCodeMap codeMap = apiCodeMapRepository.findById(mapping.getCode_map_id()).orElse(null);
					dataSet.setParameter(queryParameters);

					result = dataSet.searchAndRetrieve(codeMap.getCodeMapSql());
				} else if ("sql_update".equals(callType)) {
					// Use DataSet to execute SQL update
					DevhubApiCodeMap codeMap = apiCodeMapRepository.findById(mapping.getCode_map_id()).orElse(null);
					dataSet.setParameter(queryParameters);

					int updateCount = dataSet.update(codeMap.getCodeMapSql());

					result = updateCount; // Number of rows affected
				} else {
					// Call the method as usual for other types
					result = AbstractAnnotatedClass.callMethod(mapping.getClassName(), mapping.getMethodApi(),
							queryParameters);
				}

				responseObject.setReturnCode(0);
				responseObject.setReturnData(result);
				responseObject.setMsgDescs("Success");
			} else {
				responseObject.setReturnCode(-1);
				responseObject.setMsgDescs("No mapping found for URI: " + requestUri);
				metricsLogger.logHttpMetrics(requestUri, request.getMethod(), responseObject.getHttpStatusCode() + "",
						System.currentTimeMillis() - startTime, false);
				return responseObject;
			}
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			metricsLogger.logHttpMetrics(requestUri, request.getMethod(), responseObject.getHttpStatusCode() + "",
					duration, true);
		} catch (Exception e) {
			responseObject.setReturnCode(-1);
			responseObject.setMsgDescs("Internal Server Error: " + e.getMessage());
			metricsLogger.logHttpMetrics(requestUri, request.getMethod(), responseObject.getHttpStatusCode() + "",
					System.currentTimeMillis() - startTime, false);
			return responseObject;
		}

		return responseObject;
	}
}
