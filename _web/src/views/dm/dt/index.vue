<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('dmDataSource:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="数据库名字">
                <a-input v-model="queryParam.dataSourceName" allow-clear placeholder="请输入数据库名字"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="创建时间">
                <a-date-picker style="width: 100%" placeholder="请选择创建时间" v-model="queryParam.createTimeDate" @change="onChangecreateTime"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="创建人">
                  <a-input v-model="queryParam.createUser" allow-clear placeholder="请输入创建人"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="更新时间">
                  <a-date-picker style="width: 100%" placeholder="请选择更新时间" v-model="queryParam.updateTimeDate" @change="onChangeupdateTime"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="更新人">
                  <a-input v-model="queryParam.updateUser" allow-clear placeholder="请输入更新人"/>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="8" :sm="24" >
              <span class="table-page-search-submitButtons">
                <a-button type="primary" @click="$refs.table.refresh(true)" >查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
                <a @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
    </a-card>
    <a-card :bordered="false">
      <s-table
        ref="table"
        :columns="columns"
        :data="loadData"
        :alert="true"
        :rowKey="(record) => record.id"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
      >
        <template class="table-operator" slot="operator" v-if="hasPerm('dmDataSource:add')" >
          <a-button type="primary" v-if="hasPerm('dmDataSource:add')" icon="plus" @click="$refs.addForm.add()">新增数据库信息配置</a-button>
        </template>
        <span slot="dataSourceTypescopedSlots" slot-scope="text">
          {{ 'run_status' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('dmDataSource:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('dmDataSource:edit') & hasPerm('dmDataSource:delete')"/>
          <a-popconfirm v-if="hasPerm('dmDataSource:delete')" placement="topRight" title="确认删除？" @confirm="() => dmDataSourceDelete(record)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </s-table>
      <add-form ref="addForm" @ok="handleOk" />
      <edit-form ref="editForm" @ok="handleOk" />
    </a-card>
  </div>
</template>
<script>
  import { STable } from '@/components'
  import moment from 'moment'
  import { dmDataSourcePage, dmDataSourceDelete } from '@/api/modular/dm/dt/dmDataSourceManage'
  import addForm from './addForm.vue'
  import editForm from './editForm.vue'
  export default {
    components: {
      STable,
      addForm,
      editForm
    },
    data () {
      return {
        // 高级搜索 展开/关闭
        advanced: false,
        // 查询参数
        queryParam: {},
        // 表头
        columns: [
          {
            title: '数据库名字',
            align: 'center',
            dataIndex: 'dataSourceName'
          },
          {
            title: '数据库连接地址',
            align: 'center',
            dataIndex: 'dataSourceUrl'
          },
          {
            title: '数据库业务类型',
            align: 'center',
            dataIndex: 'dataSourceType',
            scopedSlots: { customRender: 'dataSourceTypescopedSlots' }
          },
          {
            title: '创建时间',
            align: 'center',
            dataIndex: 'createTime'
          },
          {
            title: '创建人',
            align: 'center',
            dataIndex: 'createUser'
          },
          {
            title: '更新时间',
            align: 'center',
            dataIndex: 'updateTime'
          },
          {
            title: '更新人',
            align: 'center',
            dataIndex: 'updateUser'
          }
        ],
        tstyle: { 'padding-bottom': '0px', 'margin-bottom': '10px' },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return dmDataSourcePage(Object.assign(parameter, this.switchingDate())).then((res) => {
            return res.data
          })
        },
        selectedRowKeys: [],
        selectedRows: []
      }
    },
    created () {
      if (this.hasPerm('dmDataSource:edit') || this.hasPerm('dmDataSource:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
    },
    methods: {
      moment,
      /**
       * 查询参数组装
       */
      switchingDate () {
        const queryParamcreateTime = this.queryParam.createTimeDate
        if (queryParamcreateTime != null) {
            this.queryParam.createTime = moment(queryParamcreateTime).format('YYYY-MM-DD')
            if (queryParamcreateTime.length < 1) {
                delete this.queryParam.createTime
            }
        }
        const queryParamupdateTime = this.queryParam.updateTimeDate
        if (queryParamupdateTime != null) {
            this.queryParam.updateTime = moment(queryParamupdateTime).format('YYYY-MM-DD')
            if (queryParamupdateTime.length < 1) {
                delete this.queryParam.updateTime
            }
        }
        const obj = JSON.parse(JSON.stringify(this.queryParam))
        return obj
      },
      dmDataSourceDelete (record) {
        dmDataSourceDelete(record).then((res) => {
          if (res.success) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('删除失败') // + res.message
          }
        })
      },
      toggleAdvanced () {
        this.advanced = !this.advanced
      },
      onChangecreateTime(date, dateString) {
        this.createTimeDateString = dateString
      },
      onChangeupdateTime(date, dateString) {
        this.updateTimeDateString = dateString
      },
      handleOk () {
        this.$refs.table.refresh()
      },
      onSelectChange (selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      }
    }
  }
</script>
<style lang="less">
  .table-operator {
    margin-bottom: 18px;
  }
  button {
    margin-right: 8px;
  }
</style>
