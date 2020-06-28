package team.tomato.springbootdemo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * 异常处理器
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年10月27日 下午10:16:19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public ApiResult handleRRException(RRException e){
		return ApiResult.failed(e.getCode(),e.getMessage());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ApiResult handleDuplicateKeyException(DuplicateKeyException e){
		return ApiResult.failed("数据库中已存在该记录");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ApiResult handleAccessDeniedException(AccessDeniedException e) {
		return ApiResult.failed(HttpStatus.FORBIDDEN.value(), "没有权限，请联系管理员授权");
	}
}
