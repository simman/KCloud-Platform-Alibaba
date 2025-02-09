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
package org.laokou.common.log.mapper;

import java.util.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.laokou.common.log.entity.SysAuditLogDO;
import org.laokou.common.log.vo.SysAuditLogVO;
import org.springframework.stereotype.Repository;

/**
 * @author laokou
 */
@Mapper
@Repository
public interface SysAuditLogMapper extends BaseMapper<SysAuditLogDO> {

	/**
	 * 查询资源审核日志
	 * @param businessId
	 * @param type
	 * @return
	 */
	List<SysAuditLogVO> getAuditLogList(@Param("businessId") Long businessId, @Param("type") Integer type);

}
