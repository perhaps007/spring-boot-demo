package team.tomato.springbootdemo.common.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;
import team.tomato.springbootdemo.common.utils.CommonUtils;

import java.util.Date;

/**
 * Created by Iverson on 2019/7/4.
 */
@Data
public class ApiResult {
    //TODO 成功200 失败500？？
    // 错误代码
    private int code;
    // 消息
    private String message;
    // 结果集
    private Object data;
    private String time;

    private ApiResult() {
    }

    public static ApiResult success() {
        return success(MsgCode.CODE_0000, "");
    }

    public static ApiResult success(Object data) {
        return success(MsgCode.CODE_0000, data);
    }

    public static ApiResult success(MsgCode msgCode, Object data) {
        msgCode = msgCode == null ? MsgCode.CODE_0000 : msgCode;
        return success(msgCode.getResultCode(), msgCode.getMessage(), data);
    }

    public static ApiResult success(int code, String msg, Object data) {
        ApiResult result = new ApiResult();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        result.setTime(CommonUtils.DateUtil.formatDate(new Date(), CommonUtils.DateUtil.DATE_PATTERN.yyyy_MM_dd_HH_mm_ss2));
        return result;
    }


    public static ApiResult failed(MsgCode msgCode) {
        return failed(msgCode, null);
    }

    public static ApiResult failed(MsgCode msgCode, Object data) {
        return failed(msgCode.getResultCode(), msgCode.getMessage(), data);
    }

    public static ApiResult failed(int code, String msg) {
        return failed(code, msg, null);
    }

    public static ApiResult failed(String msg) {
        return failed(500, msg);
    }

    public static ApiResult failed(int code, String msg, Object data) {
        ApiResult result = new ApiResult();
        result.setData(data);
        result.setCode(code);
        result.setMessage(msg);
        result.setTime(CommonUtils.DateUtil.formatDate(new Date(), CommonUtils.DateUtil.DATE_PATTERN.yyyy_MM_dd_HH_mm_ss2));
        return result;
    }

}
