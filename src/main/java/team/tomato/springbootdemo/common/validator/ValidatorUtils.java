package team.tomato.springbootdemo.common.validator;

import team.tomato.springbootdemo.common.exception.BusinessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author Iverson
 * @date 2020-6-12 17:06
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws BusinessException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BusinessException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new BusinessException(msg.toString());
        }
    }

    /**
     * 使用JSR303校验
     * @param object
     */
    public static void validateEntity(Object object)
            throws BusinessException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        validateEntity(constraintViolations);
        //这块看是要提示一条，还是都提示出来
        //if (!constraintViolations.isEmpty()) {
        //    if (constraintViolations.iterator().hasNext()) {
        //        throw new ValidationException(constraintViolations.iterator().next().getMessage());
        //    }
        //}
    }
}
