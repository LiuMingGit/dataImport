<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('dmSourceCompare:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="工作状态">
                <a-input v-model="queryParam.workStatus" allow-clear placeholder="请输入工作状态"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
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
        <template class="table-operator" slot="operator" v-if="hasPerm('dmSourceCompare:add')" >
          <a-button type="primary" v-if="hasPerm('dmSourceCompare:add')" icon="plus" @click="$refs.addForm.add()">新增数据库比较任务</a-button>
        </template>
        <span slot="originDataSourceScopedSlots" slot-scope="text">
          {{ myDictType(text) }}
        </span>
        <span slot="targetDataSourceScopedSlots" slot-scope="text">
          {{ myDictType(text) }}
        </span>
        <span slot="workStatusScopedSlots" slot-scope="text">
         {{ 'dc_compare_type' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a-popconfirm v-if="hasPerm('dmSourceCompare:edit') && record['workStatus'] == '1' " placement="topRight" title="确认启动？" @confirm="() => dmSourceCompareStart(record)">
            <a>启动</a>
          </a-popconfirm>
          <a-popconfirm v-if="hasPerm('dmSourceCompare:edit') && record['workStatus'] == '2' " placement="topRight" title="确认停止？" @confirm="() => dmSourceCompareStop(record)">
            <a>停止</a>
          </a-popconfirm>
          <a-divider type="vertical" v-if="hasPerm('dmSourceCompare:edit') & hasPerm('dmSourceCompare:delete')"/>
          <a-popconfirm v-if="hasPerm('dmSourceCompare:delete')" placement="topRight" title="确认删除？" @confirm="() => dmSourceCompareDelete(record)">
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
  import { dmSourceComparePage, dmSourceCompareDelete, dmSourceCompareStart, dmSourceCompareStop  } from '@/api/modular/dm/dc/dmSourceCompareManage'
  import addForm from './addForm.vue'
  import editForm from './editForm.vue'
  import {dmDataSourceList} from "@/api/modular/dm/dt/dmDataSourceManage";
  export default {
    components: {
      STable,
      addForm,
      editForm
    },
    data () {
      return {
        // 查询参数
        queryParam: {},
        // 表头
        columns: [
          {
            title: '源数据库连接',
            align: 'center',
            dataIndex: 'originDataSource',
            scopedSlots: { customRender: 'originDataSourceScopedSlots' }
          },
          {
            title: '目标数据库连接',
            align: 'center',
            dataIndex: 'targetDataSource',
            scopedSlots: { customRender: 'targetDataSourceScopedSlots' }
          },
          {
            title: '工作状态',
            align: 'center',
            dataIndex: 'workStatus',
            scopedSlots: { customRender: 'workStatusScopedSlots' }
          },
          {
            title: '执行时间',
            align: 'center',
            dataIndex: 'execTime'
          }
        ],
        dataSourceDicData:[],
        tstyle: { 'padding-bottom': '0px', 'margin-bottom': '10px' },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return dmSourceComparePage(Object.assign(parameter, this.queryParam)).then((res) => {
            return res.data
          })
        },
        selectedRowKeys: [],
        selectedRows: []
      }
    },
    created () {
      if (this.hasPerm('dmSourceCompare:edit') || this.hasPerm('dmSourceCompare:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      dmDataSourceList({}).then((res) => { if (res && res.data) this.dataSourceDicData = res.data })
    },
    methods: {
      myDictType (text) {
        for (let i = 0; i < this.dataSourceDicData.length; i++) {
          if (this.dataSourceDicData[i]['id'] == text){
            return this.dataSourceDicData[i]['dataSourceName'];
          }
        }
      },
      dmSourceCompareDelete (record) {
        dmSourceCompareDelete(record).then((res) => {
          if (res.success) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('删除失败') // + res.message
          }
        })
      },
      dmSourceCompareStart (record) {
        dmSourceCompareStart(record).then((res) => {
          if (res.success) {
            this.$message.success('启动成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('启动失败') // + res.message
          }
        })
      },
      dmSourceCompareStop (record) {
        dmSourceCompareStop(record).then((res) => {
          if (res.success) {
            this.$message.success('停止成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error('停止失败') // + res.message
          }
        })
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
