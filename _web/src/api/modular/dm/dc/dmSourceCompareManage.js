import { axios } from '@/utils/request'

/**
 * 查询数据库比较任务
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
export function dmSourceComparePage (parameter) {
  return axios({
    url: '/dmSourceCompare/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 数据库比较任务列表
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
export function dmSourceCompareList (parameter) {
  return axios({
    url: '/dmSourceCompare/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加数据库比较任务
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
export function dmSourceCompareAdd (parameter) {
  return axios({
    url: '/dmSourceCompare/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑数据库比较任务
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
export function dmSourceCompareEdit (parameter) {
  return axios({
    url: '/dmSourceCompare/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除数据库比较任务
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
export function dmSourceCompareDelete (parameter) {
  return axios({
    url: '/dmSourceCompare/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除数据库比较任务
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
export function dmSourceCompareStart (parameter) {
  return axios({
    url: '/dmSourceCompare/start',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除数据库比较任务
 *
 * @author 刘铭
 * @date 2021-07-07 15:08:48
 */
export function dmSourceCompareStop (parameter) {
  return axios({
    url: '/dmSourceCompare/stop',
    method: 'post',
    data: parameter
  })
}
