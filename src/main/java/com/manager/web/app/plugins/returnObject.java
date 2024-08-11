package com.manager.web.app.plugins;

public class returnObject {

    private int returnCode;

    private Object returnData;

    private String msgDescs;

    private String status;

    private String detail;

    private int httpStatusCode;

    public returnObject() {
        this.returnCode = 0;
        this.status = "success";
        this.detail = "";
        this.httpStatusCode = 200; // HTTP 200 OK by default
    }

    /**
     * @return int return the returnCode
     */
    public int getReturnCode() {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
        updateStatus();
    }

    /**
     * @return Object return the returnData
     */
    public Object getReturnData() {
        return returnData;
    }

    /**
     * @param returnData the returnData to set
     */
    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }

    /**
     * @return String return the msgDescs
     */
    public String getMsgDescs() {
        return msgDescs;
    }

    /**
     * @param msgDescs the msgDescs to set
     */
    public void setMsgDescs(String msgDescs) {
        this.msgDescs = msgDescs;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return String return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return int return the httpStatusCode
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * @param httpStatusCode the httpStatusCode to set
     */
    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Update status based on return code
     */
    private void updateStatus() {
        if (this.returnCode == 0) {
            this.status = "success";
            this.detail = "Operation completed successfully";
            this.httpStatusCode = 200; // HTTP 200 OK
        } else if (this.returnCode > 0) {
            this.status = "warning";
            this.detail = "Operation completed with warnings";
            this.httpStatusCode = 400; // HTTP 400 Bad Request
        } else {
            this.status = "error";
            this.detail = "Operation failed";
            this.httpStatusCode = 500; // HTTP 500 Internal Server Error
        }
    }
}