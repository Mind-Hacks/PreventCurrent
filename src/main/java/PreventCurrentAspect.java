import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @description 防止并发或者重复提交表单切面
 * @user MindHacks
 * @Date 2016年12月7日 上午11:52:34
 * @version 1.0.0
 */
public class PreventCurrentAspect {
    public final static String KEY_PREFIX = "XXXXXX_KEY";
    
    private static Logger logger=LoggerFactory.getLogger(PreventCurrentAspect.class);
   
    public Object currentAspectPoint(ProceedingJoinPoint point, PreventCurrent current,String uuid) throws Throwable {

        String targetClass = point.getTarget().getClass().getName();
        String targetMethod = point.getSignature().getName();
        String redisConcurrent =KEY_PREFIX+":"+targetClass +":"+ targetMethod +":"+ uuid;
        Long redisResult =null;
        Object result =null;
        try {
            redisResult=RedisUtil.setByteNxValue(redisConcurrent, "exist");
            if (redisResult.longValue() == 0) {
                logger.error(current.desc()+"重复提交:"+uuid+new Date());
                throw new PreventCurrentException("请勿重复提交数据，请稍后再试！");
            }
            result = point.proceed();
        } finally {
            if(redisResult.longValue() == 1){//仅仅当redis设置为1，也就是 point.proceed()执行方法时候，抛出异常
                if(!RedisUtil.deleteByteData(redisConcurrent)){
                    logger.error("防止并发或者重复提交删除KEY失败={}",redisConcurrent);
                    RedisUtil.deleteByteData(redisConcurrent);
                }
            }
        }
        return result;
    }
    
}
