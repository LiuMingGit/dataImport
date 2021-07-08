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
package vip.xiaonuo.dm.dc.service.impl;

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
import vip.xiaonuo.dm.dc.entity.DmSourceCompare;
import vip.xiaonuo.dm.dc.enums.DmSourceCompareExceptionEnum;
import vip.xiaonuo.dm.dc.mapper.DmSourceCompareMapper;
import vip.xiaonuo.dm.dc.param.DmSourceCompareParam;
import vip.xiaonuo.dm.dc.service.DmSourceCompareService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据库比较任务service接口实现类
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
@Service
public class DmSourceCompareServiceImpl extends ServiceImpl<DmSourceCompareMapper, DmSourceCompare> implements DmSourceCompareService {

    @Override
    public PageResult<DmSourceCompare> page(DmSourceCompareParam dmSourceCompareParam) {
        QueryWrapper<DmSourceCompare> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(dmSourceCompareParam)) {

            // 根据工作状态 查询
            if (ObjectUtil.isNotEmpty(dmSourceCompareParam.getWorkStatus())) {
                queryWrapper.lambda().eq(DmSourceCompare::getWorkStatus, dmSourceCompareParam.getWorkStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<DmSourceCompare> list(DmSourceCompareParam dmSourceCompareParam) {
        return this.list();
    }

    @Override
    public void add(DmSourceCompareParam dmSourceCompareParam) {
        DmSourceCompare dmSourceCompare = new DmSourceCompare();
        BeanUtil.copyProperties(dmSourceCompareParam, dmSourceCompare);
        this.save(dmSourceCompare);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(DmSourceCompareParam dmSourceCompareParam) {
        this.removeById(dmSourceCompareParam.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DmSourceCompareParam dmSourceCompareParam) {
        DmSourceCompare dmSourceCompare = this.queryDmSourceCompare(dmSourceCompareParam);
        BeanUtil.copyProperties(dmSourceCompareParam, dmSourceCompare);
        this.updateById(dmSourceCompare);
    }

    @Override
    public DmSourceCompare detail(DmSourceCompareParam dmSourceCompareParam) {
        return this.queryDmSourceCompare(dmSourceCompareParam);
    }

    @Override
    public void start(Long id) {
        DmSourceCompare compare = this.getById(id);
        compare.setWorkStatus("2");
        this.updateById(compare);
        List<String> strings = this.selectTableList();
        for (String string : strings) {
            System.err.println(string);
        }
    }

    @Override
    public void stop(Long id) {
        DmSourceCompare compare = this.getById(id);
        compare.setWorkStatus("4");
        this.updateById(compare);
    }

    @Override
    public List<String> selectTableList() {
        return this.baseMapper.selectTableList();
    }

    @Override
    public List<Map<String, Object>> selectTableInfo(String tableName) {
        return this.baseMapper.selectTableInfo(tableName);
    }

    /**
     * 获取数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    private DmSourceCompare queryDmSourceCompare(DmSourceCompareParam dmSourceCompareParam) {
        DmSourceCompare dmSourceCompare = this.getById(dmSourceCompareParam.getId());
        if (ObjectUtil.isNull(dmSourceCompare)) {
            throw new ServiceException(DmSourceCompareExceptionEnum.NOT_EXIST);
        }
        return dmSourceCompare;
    }
}
