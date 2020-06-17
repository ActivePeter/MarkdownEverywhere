/*
* 过滤特殊字符
* */
export function filter(s) {
  let pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]")
  let rs = "";
  for (let i = 0; i < s.length; i++) {
    rs = rs + s.substr(i, 1).replace(pattern, '');
  }
  return rs;
};

// <!--验证邮箱是否合法-->
export function checkUsername(rule, value, callback) {
  let re = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
  if (value === '') {
    callback(new Error('请输入邮箱'))
  } else if (!re.test(value)) {
    callback(new Error('邮箱不合法'))
  } else {
    callback()
  }
};
var pw1=""
// <!--验证密码-->
export function checkPassword(rule, value, callback) {
  pw1=value;
  let re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
  if (value === "") {
    callback(new Error("请输入密码"))
  } else if (!re.test(value)) {
    callback(new Error("密码为6至20位数字+字母"))
  } else {
    callback()
  }
};
// <!--验证二次密码-->
export function checkPassword2(rule, value, callback) {
  let re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
  if (value === "") {
    callback(new Error("请输入密码"))
  } else if (!re.test(value)) {
    callback(new Error("密码为6至20位数字+字母"))
  }else if(value!=pw1){
    callback(new Error("两次密码输入不一致"))
  } else {
    callback()
  }
};

// <!--验证校验码-->
export function checkCode(rule, value, callback) {
  let re = /^\d{6}$/;
  if (value === "") {
    callback(new Error("请输入验证码"))
  } else if (!re.test(value)) {
    callback(new Error("验证码格式有误"))
  } else {
    callback()
  }
};
