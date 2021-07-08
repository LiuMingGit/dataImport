import { axios } from '@/utils/request'

/**
 * 查询数据库信息配置
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
export function dmDataSourcePage (parameter) {
  return axios({
    url: '/dmDataSource/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 数据库信息配置列表
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
export function dmDataSourceList (parameter) {
  return axios({
    url: '/dmDataSource/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加数据库信息配置
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
export function dmDataSourceAdd (parameter) {
  return axios({
    url: '/dmDataSource/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑数据库信息配置
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
export function dmDataSourceEdit (parameter) {
  return axios({
    url: '/dmDataSource/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除数据库信息配置
 *
 * @author 刘铭
 * @date 2021-07-07 11:10:45
 */
export function dmDataSourceDelete (parameter) {
  return axios({
    url: '/dmDataSource/delete',
    method: 'post',
    data: parameter
  })
}
