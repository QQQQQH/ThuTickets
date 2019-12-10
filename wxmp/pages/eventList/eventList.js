const app = getApp()

Page({
  data: {
    eventList: [],
    gotEventList: false,
  },

  onLoad: function(options) {
    this.refreshPage()
  },

  onPullDownRefresh: function() {
    // wx.showLoading({
    //   title: '正在加载...',
    // })
    this.refreshPage()
  },

  refreshPage: function() {
    wx.request({
      url: app.globalData.serverIp + '/user/events/list',
      headers: {
        'Content-Type': 'application/json'
      },
      success: res => {
        console.log(res)
        if (res.data.status == 200) {
          let eventList = res.data.data
          let len = eventList.length
          for (let i = 0; i < len; i++) {
            eventList[i].imgPath = app.globalData.serverIp + eventList[i].imgPath
          }
          this.setData({
            eventList: res.data.data,
          })
          if (len > 0) {
            this.setData({
              gotEventList: true
            })
          }
          console.log('event list:')
          console.log(this.data.eventList)
        } else {
          wx.showToast({
            title: '获取活动失败',
            icon: 'none',
            duration: 1000
          })
        }
      },
      fail: error => {
        //调用服务端登录接口失败
        wx.showToast({
          title: '服务器连接错误',
          icon: 'none',
          duration: 1000
        })
        console.log(error);
      }
    })
  },
})