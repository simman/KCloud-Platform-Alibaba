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
package org.laokou.common.tenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laokou.common.tenant.entity.SysPackageMenuDO;
import org.laokou.common.tenant.service.SysPackageMenuService;
import org.laokou.common.tenant.mapper.SysPackageMenuMapper;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author laokou
 */
@Service
public class SysPackageMenuServiceImpl extends ServiceImpl<SysPackageMenuMapper, SysPackageMenuDO>
		implements SysPackageMenuService {

	@Override
	public void insertBatch(List<SysPackageMenuDO> list) {
		this.baseMapper.insertBatch(list);
	}

}
