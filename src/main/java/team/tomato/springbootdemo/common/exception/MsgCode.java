package team.tomato.springbootdemo.common.exception;

/**
 * Created by Iverson on 2019/7/4.
 */
public enum MsgCode {

    // 业务级错误代码
    CODE_0000(0000, "请求成功。"),
    //示例，如果编码不用区分的话也可以都用0001
    CODE_0001(0001, "请求失败。"),
    CODE_0002(0002, "用户名或密码不能为空"),
    CODE_0003(0003, "token不能为空");

    MsgCode(int resultCode, String msg) {
        this.resultCode = resultCode;
        this.message = msg;
    }

    private int resultCode;
    private String message;

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }
}
