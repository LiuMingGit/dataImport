<template>
  <a-modal
    title="编辑数据库信息配置"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item v-show="false"><a-input v-decorator="['id']" /></a-form-item>
        <a-form-item
          label="数据库名字"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入数据库名字" v-decorator="['dataSourceName', {rules: [{required: true, message: '请输入数据库名字！'}]}]" />
        </a-form-item>
        <a-form-item
          label="数据库连接地址"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入数据库连接地址" v-decorator="['dataSourceUrl', {rules: [{required: true, message: '请输入数据库连接地址！'}]}]" />
        </a-form-item>
        <a-form-item
          label="数据库用户名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入数据库用户名" v-decorator="['dataSourceUsername', {rules: [{required: true, message: '请输入数据库用户名！'}]}]" />
        </a-form-item>
        <a-form-item
          label="数据库密码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入数据库密码" v-decorator="['dataSourcePassword', {rules: [{required: true, message: '请输入数据库密码！'}]}]" />
        </a-form-item>
        <a-form-item
          label="数据库业务类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择数据库业务类型" v-decorator="['dataSourceType', {rules: [{ required: true, message: '请选择数据库业务类型！' }]}]">
            <a-select-option v-for="(item,index) in dataSourceTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="创建时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择创建时间" v-decorator="['createTime',{rules: [{ required: true, message: '请选择创建时间！' }]}]" @change="onChangecreateTime"/>
        </a-form-item>
        <a-form-item
          label="创建人"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入创建人" v-decorator="['createUser', {rules: [{required: true, message: '请输入创建人！'}]}]" />
        </a-form-item>
        <a-form-item
          label="更新时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择更新时间" v-decorator="['updateTime',{rules: [{ required: true, message: '请选择更新时间！' }]}]" @change="onChangeupdateTime"/>
        </a-form-item>
        <a-form-item
          label="更新人"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入更新人" v-decorator="['updateUser', {rules: [{required: true, message: '请输入更新人！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import moment from 'moment'
  import { dmDataSourceEdit } from '@/api/modular/dm/dt/dmDataSourceManage'
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
        dataSourceTypeData: [],
        createTimeDateString: '',
        updateTimeDateString: '',
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this)
      }
    },
    methods: {
      moment,
      // 初始化方法
      edit (record) {
        this.visible = true
        const dataSourceTypeOption = this.$options
        this.dataSourceTypeData = dataSourceTypeOption.filters['dictData']('dm_dt_type')
        setTimeout(() => {
          this.form.setFieldsValue(
            {
              id: record.id,
              dataSourceName: record.dataSourceName,
              dataSourceUrl: record.dataSourceUrl,
              dataSourceUsername: record.dataSourceUsername,
              dataSourcePassword: record.dataSourcePassword,
              dataSourceType: record.dataSourceType,
              createUser: record.createUser,
              updateUser: record.updateUser
            }
          )
        }, 100)
        // 时间单独处理
        if (record.createTime != null) {
            this.form.getFieldDecorator('createTime', { initialValue: moment(record.createTime, 'YYYY-MM-DD') })
        }
        this.createTimeDateString = moment(record.createTime).format('YYYY-MM-DD')
        // 时间单独处理
        if (record.updateTime != null) {
            this.form.getFieldDecorator('updateTime', { initialValue: moment(record.updateTime, 'YYYY-MM-DD') })
        }
        this.updateTimeDateString = moment(record.updateTime).format('YYYY-MM-DD')
      },
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
            values.createTime = this.createTimeDateString
            values.updateTime = this.updateTimeDateString
            dmDataSourceEdit(values).then((res) => {
              if (res.success) {
                this.$message.success('编辑成功')
                this.confirmLoading = false
                this.$emit('ok', values)
                this.handleCancel()
              } else {
                this.$message.error('编辑失败')//  + res.message
              }
            }).finally((res) => {
              this.confirmLoading = false
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      onChangecreateTime(date, dateString) {
        this.createTimeDateString = dateString
      },
      onChangeupdateTime(date, dateString) {
        this.updateTimeDateString = dateString
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      }
    }
  }
</script>
