<template>
  <a-modal
    title="新增数据库比较任务"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="源数据库连接"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-select style="width: 100%" placeholder="请输入源数据库连接" v-decorator="['originDataSource', {rules: [{ required: true, message: '请输入源数据库连接！' }]}]">
            <a-select-option v-for="(item,index) in dataSourceDic" :key="index" :value="item.id">{{ item.dataSourceName }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="目标数据库连接"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-select style="width: 100%" placeholder="请输入目标数据库连接" v-decorator="['targetDataSource', {rules: [{ required: true, message: '请输入目标数据库连接！' }]}]">
            <a-select-option v-for="(item,index) in dataSourceDic" :key="index" :value="item.id">{{ item.dataSourceName }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { dmSourceCompareAdd } from '@/api/modular/dm/dc/dmSourceCompareManage'
  import { dmDataSourceList } from '@/api/modular/dm/dt/dmDataSourceManage'
  export default {
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        dataSourceDic: [],
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this)
      }
    },
    methods: {
      // 初始化方法
      add (record) {
        this.visible = true
        dmDataSourceList({}).then((res) => { if (res && res.data) this.dataSourceDic = res.data })
      },
      /**
       * 提交表单
       */
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            for (const key in values) {
              if (typeof (values[key]) === 'object') {
                values[key] = JSON.stringify(values[key])
              }
            }
            dmSourceCompareAdd(values).then((res) => {
              if (res.success) {
                this.$message.success('新增成功')
                this.confirmLoading = false
                this.$emit('ok', values)
                this.handleCancel()
              } else {
                this.$message.error('新增失败')// + res.message
              }
            }).finally((res) => {
              this.confirmLoading = false
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      }
    }
  }
</script>
