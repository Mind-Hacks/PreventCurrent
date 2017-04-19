import java.lang.annotation.*;

/**
 * @description 分布式，防止表单重复提交（或者并发）公共组件
 * @user MindHacks
 * @Date 2016年12月7日 上午11:48:40
 * @version 1.0.0
 */
@Target({ElementType.METHOD})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented 
public @interface PreventCurrent {   
    //自定义注解的属性，default是设置默认值
    String desc() default "防并发注解方法";   
}  