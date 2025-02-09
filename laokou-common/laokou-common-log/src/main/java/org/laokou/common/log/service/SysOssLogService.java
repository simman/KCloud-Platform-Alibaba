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
package org.laokou.common.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laokou.common.log.entity.SysOssLogDO;
import org.laokou.common.log.event.OssLogEvent;

/**
 * @author laokou
 */
public interface SysOssLogService extends IService<SysOssLogDO> {

	/**
	 * 新增日志
	 * @param event
	 * @return
	 */
	Boolean insertLog(OssLogEvent event);

	/**
	 * 获取文件日志
	 * @param md5
	 * @return
	 */
	SysOssLogDO getLogByMd5(String md5);

}
