const app = getApp()

Page({
  data: {
    fromPage: null,
    ellipsis: true, // 文字是否收起，默认收起
    eventInfo: null, // 活动详情
    ticketsLeft: false // 有余票
  },

  onLoad: function(options) {
    this.setData({
      fromPage: options.fromPage
    })
    console.log('from page: ' + this.data.fromPage)
    this.getCurrenteventInfo(options.id);
  },

  getCurrenteventInfo(eventId) {
    wx.request({
      url: app.globalData.serverIp + '/user/events/detail?eventid=' + eventId,
      success: res => {
        this.setData({
          eventInfo: res.data.data
        })
        let s = 'eventInfo.imgPath'
        let path = app.globalData.serverIp + this.data.eventInfo.imgPath
        this.setData({
          [s]: path
        })
        if (this.data.eventInfo.ticketsLeft) {
          this.setData({
            ticketsLeft: true
          })
        }
        console.log('event detail:')
        console.log(this.data.eventInfo)
      }
    })
  },

  ellipsis: function() {
    //简介展开
    var value = !this.data.ellipsis;
    this.setData({
      ellipsis: value
    })
  },

  handleGetTicket: function(e) {
    wx.request({
      url: app.globalData.serverIp + '/user/buy-ticket?skey=' +
        wx.getStorageSync('skey') + '&eventid=' + this.data.eventInfo.eventid,
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: res => {
        console.log(res);
        if (res.data.status == 200) {
          wx.showToast({
            title: '抢票成功',
            duration: 1000
          })
        } else {
          wx.showToast({
            icon: 'none',
            title: '抢票失败，请检查是否已绑定学号',
            duration: 1000
          })
        }
      },
      fail: function(error) {
        wx.showToast({
          icon: 'none',
          title: '服务器连接错误',
          duration: 1000
        })
        console.log('服务器连接错误');
      }
    })
  },

  handleReturnTicket: function(e) {
    wx.showModal({
      title: '警告',
      content: '确认要退票吗？',
      success: res => {
        if (res.confirm) {
          this.returnTicket()
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  returnTicket: function() {
    wx.showToast({
      title: '退票成功',
      duration: 1000
    })
  }

})