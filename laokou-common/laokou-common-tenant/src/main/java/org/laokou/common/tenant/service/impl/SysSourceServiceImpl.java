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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laokou.common.core.vo.OptionVO;
import org.laokou.common.tenant.entity.SysSourceDO;
import org.laokou.common.tenant.mapper.SysSourceMapper;
import org.laokou.common.tenant.service.SysSourceService;
import org.laokou.common.tenant.vo.SysSourceVO;
import org.laokou.common.tenant.qo.SysSourceQo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author laokou
 */
@Service
public class SysSourceServiceImpl extends ServiceImpl<SysSourceMapper, SysSourceDO> implements SysSourceService {

	@Override
	public IPage<SysSourceVO> querySourcePage(IPage<SysSourceVO> page, SysSourceQo qo) {
		return this.baseMapper.querySourcePage(page, qo);
	}

	@Override
	public Integer getVersion(Long id) {
		return this.baseMapper.getVersion(id);
	}

	@Override
	public void deleteSource(Long id) {
		this.baseMapper.deleteById(id);
	}

	@Override
	public String querySourceName(Long tenantId) {
		return this.baseMapper.querySourceName(tenantId);
	}

	@Override
	public SysSourceVO querySource(String sourceName) {
		return this.baseMapper.querySource(sourceName);
	}

	@Override
	public SysSourceVO getSourceById(Long id) {
		return this.baseMapper.getSourceById(id);
	}

	@Override
	public List<OptionVO> getOptionList() {
		return this.baseMapper.getOptionList();
	}

}
