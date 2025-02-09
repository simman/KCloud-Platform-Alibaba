/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.laokou.common.jasypt.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.laokou.common.i18n.core.HttpResult;
import org.laokou.common.jasypt.annotation.Jasypt;
import org.laokou.common.jasypt.utils.AesUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author laokou
 */
@Aspect
@Component
@Slf4j
public class AesAspect {

	@Around("@annotation(org.laokou.common.jasypt.annotation.Jasypt)")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Jasypt jasypt = AnnotationUtils.findAnnotation(method, Jasypt.class);
		Object proceed = point.proceed();
		switch (Objects.requireNonNull(jasypt).type()) {
			case AES -> {
				if (proceed instanceof HttpResult<?> result) {
					Object data = result.getData();
					AesUtil.transform(data);
				}
			}
			case MD5 -> {
			}
			default -> {
			}
		}
		return proceed;
	}

}
