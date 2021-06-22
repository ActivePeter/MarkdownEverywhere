// 在 main.js 中引入 FastAMap
import Vue from 'vue'
import FastAMap from 'fast-amap'

Vue.use(FastAMap)

// 设置你需要加载的高德地图版本
FastAMap.mapOptions.setOptions({
  key: '111',
  version: '1.4.15',
})
// key :d2d76e2274bf5973ecfb1f68454b6f3b
