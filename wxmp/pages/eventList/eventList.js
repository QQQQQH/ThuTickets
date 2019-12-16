const app = getApp()
const promisify = require('../../utils/util.js')

Page({
  data: {
    eventList: [],
    gotEventList: -1,
  },

  onLoad: function(options) {
  },

  onShow: function(){
    this.refreshPage()
  },

  onPullDownRefresh: function() {
    wx.stopPullDownRefresh()
    this.refreshPage()
  },

  refreshPage: function() {
    wx.request({
      url: app.globalData.serverIp + '/user/events/list?expired=1',
      headers: {
        'Content-Type': 'application/json'
      },
      success: res => {
        console.log('res:')
        console.log(res)
        if (res.data.status == 200) {
          let eventList = res.data.data
          let len = eventList.length
          for (let i = 0; i < len; i++) {
            eventList[i].imgPath = app.globalData.serverIp + eventList[i].imgPath
          }
          this.setData({
            eventList: res.data.data,
            gotEventList: len > 0
          })
          console.log('event list:')
          console.log(this.data.eventList)
        } else {
          wx.showToast({
            title: '获取活动失败',
            icon: 'none',
          })
        }
      },
      fail: error => {
        //调用服务端登录接口失败
        wx.showToast({
          title: '服务器连接错误',
          icon: 'none',
        })
        console.log(error);
      }
    })
  },
})