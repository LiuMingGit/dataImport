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
package vip.xiaonuo.dm.dt.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vip.xiaonuo.dm.dt.param.DmDataSourceParam;
import vip.xiaonuo.dm.dt.service.DmDataSourceService;

import javax.annotation.Resource;

/**
 * 数据库信息配置控制器
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
@RestController
public class DmDataSourceController {

    @Resource
    private DmDataSourceService dmDataSourceService;

    /**
     * 查询数据库信息配置
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    @Permission
    @GetMapping("/dmDataSource/page")
    @BusinessLog(title = "数据库信息配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(DmDataSourceParam dmDataSourceParam) {
        return new SuccessResponseData(dmDataSourceService.page(dmDataSourceParam));
    }

    /**
     * 添加数据库信息配置
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    @Permission
    @PostMapping("/dmDataSource/add")
    @BusinessLog(title = "数据库信息配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(DmDataSourceParam.add.class) DmDataSourceParam dmDataSourceParam) {
            dmDataSourceService.add(dmDataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据库信息配置
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    @Permission
    @PostMapping("/dmDataSource/delete")
    @BusinessLog(title = "数据库信息配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(DmDataSourceParam.delete.class) DmDataSourceParam dmDataSourceParam) {
            dmDataSourceService.delete(dmDataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑数据库信息配置
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    @Permission
    @PostMapping("/dmDataSource/edit")
    @BusinessLog(title = "数据库信息配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(DmDataSourceParam.edit.class) DmDataSourceParam dmDataSourceParam) {
            dmDataSourceService.edit(dmDataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 查看数据库信息配置
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    @Permission
    @GetMapping("/dmDataSource/detail")
    @BusinessLog(title = "数据库信息配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(DmDataSourceParam.detail.class) DmDataSourceParam dmDataSourceParam) {
        return new SuccessResponseData(dmDataSourceService.detail(dmDataSourceParam));
    }

    /**
     * 数据库信息配置列表
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    @Permission
    @GetMapping("/dmDataSource/list")
    @BusinessLog(title = "数据库信息配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(DmDataSourceParam dmDataSourceParam) {
        return new SuccessResponseData(dmDataSourceService.list(dmDataSourceParam));
    }


    /**
     * 数据库信息配置列表
     *
     * @author 刘铭
     * @date 2021-07-07 11:10:45
     */
    @GetMapping("/dmDataSource/dic")
    @BusinessLog(title = "数据库信息配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData dic() {
        return new SuccessResponseData(dmDataSourceService.list());
    }

}
