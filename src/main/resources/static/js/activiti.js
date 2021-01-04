// Xianlu Tech 扩展 v1.0
/**
 * 计算时间差，单位秒
 * @param startDateStr
 * @param endDateStr
 */
function calcTotalSecond(startDateStr, endDateStr) {
  var date1 = new Date(startDateStr);             // 开始时间
  var date2 = new Date(endDateStr);               // 结束时间
  var timeSub = date2.getTime() - date1.getTime();  // 时间差毫秒
  return timeSub / 1000;
}

/**
 * 计算出相差天数
 * @param secondSub
 */
function formatTotalDateSub (secondSub) {
  var days = Math.floor(secondSub / (24 * 3600));     // 计算出小时数
  var leave1 = secondSub % (24*3600) ;                // 计算天数后剩余的毫秒数
  var hours = Math.floor(leave1 / 3600);              // 计算相差分钟数
  var leave2 = leave1 % (3600);                       // 计算小时数后剩余的毫秒数
  var minutes = Math.floor(leave2 / 60);              // 计算相差秒数
  var leave3 = leave2 % 60;                           // 计算分钟数后剩余的毫秒数
  var seconds = Math.round(leave3);
  return days + " 天 " + hours + " 时 " + minutes + " 分 " + seconds + ' 秒';
}

/* 查看审批历史 */
function showHistoryDialog(instanceId) {
  var url = ctx + 'process/historyList/' + instanceId;
  $.modal.open("查看审批历史", url, null, null, null, true);
}

function showProcessImgDialog(instanceId) {
  var url = ctx + 'process/processImg/' + instanceId;
  $.modal.open("查看流程图", url, null, null, null, true);
}

/* 选择用户 */
function selectUser(taskId) {
  var url = ctx + 'user/authUser/selectUser?taskId=' + taskId;
  $.modal.open("关联系统用户", url);
}

function showFormDialog(instanceId, module) {
  var url = ctx + module + "/showFormDialog/" + instanceId;
  $.modal.open('申请详情', url, null, null, null, true);
}
