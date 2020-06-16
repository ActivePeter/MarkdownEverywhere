import {request} from "@/network/request";

export function getUsersMultiData() {
  return request({
    url:'/api/users/'
  })
}

export function Login(data) {
  return request({
    method:'post',
    url:'/api/login/',
    data
  })
}

export function Register(data) {
  return request({
    method:'post',
    url:'/api/register/',
    data
  })
}

export function GetCode(data) {
  return request({
    method:'post',
    url:'/api/emailcodes/',
    data
  },60000)
}

export function GetSms(data) {
  return request({
    method:'get',
    url:'/api/sms/',
    data
  })
}

