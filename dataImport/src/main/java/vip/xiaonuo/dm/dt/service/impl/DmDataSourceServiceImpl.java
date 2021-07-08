/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.dm.dt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.core.consts.CommonConstant;
import vip.xiaonuo.core.enums.CommonStatusEnum;
import vip.xiaonuo.core.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.dm.dt.entity.DmDataSource;
import vip.xiaonuo.dm.dt.enums.DmDataSourceExceptionEnum;
import vip.xiaonuo.dm.dt.mapper.DmDataSourceMapper;
import vip.xiaonuo.dm.dt.param.DmDataSourceParam;
import vip.xiaonuo.dm.dt.service.DmDataSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库信息配置service接口实现类
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
@Service
public class DmDataSourceServiceImpl extends ServiceImpl<DmDataSourceMapper, DmDataSource> implements DmDataSourceService {

    @Override
    public PageResult<DmDataSource> page(DmDataSourceParam dmDataSourceParam) {
        QueryWrapper<DmDataSource> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(dmDataSourceParam)) {

            // 根据数据库名字 查询
            if (ObjectUtil.isNotEmpty(dmDataSourceParam.getDataSourceName())) {
                queryWrapper.lambda().eq(DmDataSource::getDataSourceName, dmDataSourceParam.getDataSourceName());
            }
            // 根据创建时间 查询
            if (ObjectUtil.isNotEmpty(dmDataSourceParam.getCreateTime())) {
                queryWrapper.lambda().eq(DmDataSource::getCreateTime, dmDataSourceParam.getCreateTime());
            }
            // 根据创建人 查询
            if (ObjectUtil.isNotEmpty(dmDataSourceParam.getCreateUser())) {
                queryWrapper.lambda().eq(DmDataSource::getCreateUser, dmDataSourceParam.getCreateUser());
            }
            // 根据更新时间 查询
            if (ObjectUtil.isNotEmpty(dmDataSourceParam.getUpdateTime())) {
                queryWrapper.lambda().eq(DmDataSource::getUpdateTime, dmDataSourceParam.getUpdateTime());
            }
            // 根据更新人 查询
            if (ObjectUtil.isNotEmpty(dmDataSourceParam.getUpdateUser())) {
                queryWrapper.lambda().eq(DmDataSource::getUpdateUser, dmDataSourceParam.getUpdateUser());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<DmDataSource> list(DmDataSourceParam dmDataSourceParam) {
        return this.list();
    }

    @Override
    public void add(DmDataSourceParam dmDataSourceParam) {
        DmDataSource dmDataSource = new DmDataSource();
        BeanUtil.copyProperties(dmDataSourceParam, dmDataSource);
        this.save(dmDataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(DmDataSourceParam dmDataSourceParam) {
        this.removeById(dmDataSourceParam.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DmDataSourceParam dmDataSourceParam) {
        DmDataSource dmDataSource = this.queryDmDataSource(dmDataSourceParam);
        BeanUtil.copyProperties(dmDataSourceParam, dmDataSource);
        this.updateById(dmDataSource);
    }

    @Override
    public DmDataSource detail(DmDataSourceParam dmDataSourceParam) {
        return this.queryDmDataSource(dmDataSourceParam);
    }

    /**
     * 获取数据库信息配置
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    private DmDataSource queryDmDataSource(DmDataSourceParam dmDataSourceParam) {
        DmDataSource dmDataSource = this.getById(dmDataSourceParam.getId());
        if (ObjectUtil.isNull(dmDataSource)) {
            throw new ServiceException(DmDataSourceExceptionEnum.NOT_EXIST);
        }
        return dmDataSource;
    }


}
