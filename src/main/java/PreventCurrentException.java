/**
 * @description 防止并发异常
 * @user MindHacks
 * @Date 2016年12月12日 下午2:44:40
 * @version 1.0.0
 */
public class PreventCurrentException extends RuntimeException {

    public PreventCurrentException(String msg) {
        super(msg);
    }
}
