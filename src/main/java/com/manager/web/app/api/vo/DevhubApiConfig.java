package com.manager.web.app.api.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "devhub_api_config")
public class DevhubApiConfig {

    @Id
    @Column(name = "URI_API", nullable = false, length = 255)
    private String uriApi;

    @Column(name = "METHOD", nullable = false, length = 10)
    private String method;

    @Column(name = "CALL_MOD", length = 50)
    private String callMod;

    @Column(name = "PARAM")
    private String param;

    @Column(name = "NEED_CHECK")
    private Boolean needCheck;

    @Column(name = "NEED_LOG")
    private Boolean needLog;

    // Getters and Setters

    public String getUriApi() {
        return uriApi;
    }

    public void setUriApi(String uriApi) {
        this.uriApi = uriApi;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCallMod() {
        return callMod;
    }

    public void setCallMod(String callMod) {
        this.callMod = callMod;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Boolean getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(Boolean needCheck) {
        this.needCheck = needCheck;
    }

    public Boolean getNeedLog() {
        return needLog;
    }

    public void setNeedLog(Boolean needLog) {
        this.needLog = needLog;
    }
    
}
