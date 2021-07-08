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
package vip.xiaonuo.dm.dc.controller;

import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.dm.dc.param.DmSourceCompareParam;
import vip.xiaonuo.dm.dc.service.DmSourceCompareService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * 数据库比较任务控制器
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
@RestController
public class DmSourceCompareController {

    @Resource
    private DmSourceCompareService dmSourceCompareService;

    /**
     * 查询数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @Permission
    @GetMapping("/dmSourceCompare/page")
    @BusinessLog(title = "数据库比较任务_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(DmSourceCompareParam dmSourceCompareParam) {
        return new SuccessResponseData(dmSourceCompareService.page(dmSourceCompareParam));
    }

    /**
     * 添加数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @Permission
    @PostMapping("/dmSourceCompare/add")
    @BusinessLog(title = "数据库比较任务_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(DmSourceCompareParam.add.class) DmSourceCompareParam dmSourceCompareParam) {
        dmSourceCompareParam.setWorkStatus("1");
        dmSourceCompareService.add(dmSourceCompareParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @Permission
    @PostMapping("/dmSourceCompare/delete")
    @BusinessLog(title = "数据库比较任务_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(DmSourceCompareParam.delete.class) DmSourceCompareParam dmSourceCompareParam) {
        dmSourceCompareService.delete(dmSourceCompareParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @Permission
    @PostMapping("/dmSourceCompare/edit")
    @BusinessLog(title = "数据库比较任务_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(DmSourceCompareParam.edit.class) DmSourceCompareParam dmSourceCompareParam) {
        dmSourceCompareService.edit(dmSourceCompareParam);
        return new SuccessResponseData();
    }

    /**
     * 查看数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @Permission
    @GetMapping("/dmSourceCompare/detail")
    @BusinessLog(title = "数据库比较任务_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(DmSourceCompareParam.detail.class) DmSourceCompareParam dmSourceCompareParam) {
        return new SuccessResponseData(dmSourceCompareService.detail(dmSourceCompareParam));
    }

    /**
     * 数据库比较任务列表
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @Permission
    @GetMapping("/dmSourceCompare/list")
    @BusinessLog(title = "数据库比较任务_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(DmSourceCompareParam dmSourceCompareParam) {
        return new SuccessResponseData(dmSourceCompareService.list(dmSourceCompareParam));
    }


    /**
     * 启动数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @PostMapping("/dmSourceCompare/start")
    @BusinessLog(title = "数据库比较任务_启动", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData start(@RequestBody @Validated(DmSourceCompareParam.add.class) DmSourceCompareParam dmSourceCompareParam) {
        Long id = dmSourceCompareParam.getId();
        dmSourceCompareService.start(id);
        return new SuccessResponseData();
    }

    /**
     * 停止数据库比较任务
     *
     * @author 刘铭
     * @date 2021-07-07 15:08:48
     */
    @PostMapping("/dmSourceCompare/stop")
    @BusinessLog(title = "数据库比较任务_停止", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData stop(@RequestBody @Validated(DmSourceCompareParam.add.class) DmSourceCompareParam dmSourceCompareParam) {
        Long id = dmSourceCompareParam.getId();
        dmSourceCompareService.stop(id);
        return new SuccessResponseData();
    }
}
