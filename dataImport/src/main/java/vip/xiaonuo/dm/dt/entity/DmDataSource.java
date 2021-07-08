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
package vip.xiaonuo.dm.dt.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 数据库信息配置
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("DM_DATA_SOURCE")
public class DmDataSource extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据库名字
     */
    private String dataSourceName;

    /**
     * 数据库连接地址
     */
    private String dataSourceUrl;

    /**
     * 数据库用户名
     */
    private String dataSourceUsername;

    /**
     * 数据库密码
     */
    private String dataSourcePassword;

    /**
     * 数据库业务类型
     */
    private String dataSourceType;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date updateTime;

    /**
     * 更新人
     */
    private Long updateUser;

}
