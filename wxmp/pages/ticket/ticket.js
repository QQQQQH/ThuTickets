const app = getApp()

Page({
  data: {
    ticketId: null,
    ticketInfo: null, // 活动详情
  },

  onLoad: function (options) {
    this.setData({
      ticketId: options.ticketId
    })
    console.log('ticket: ticketId=' + this.data.ticketId)
    this.refreshPage(this.data.ticketId);
  },

  onPullDownRefresh: function () {
    wx.stopPullDownRefresh()
    this.refreshPage(this.data.ticketId)
  },

  // 还没有做修改
  refreshPage: function (ticketId) {
    wx.request({
      url: app.globalData.serverIp + '/user/tickets/detail?ticketid=' + ticketId,
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: res => {
        console.log(res)
        if (res.data.status == 200) {
          let ticketInfo = res.data.data
          this.setData({
            ticketInfo: ticketInfo
          })
          console.log('ticket detail:')
          console.log(this.data.ticketInfo)
        } else {
          wx.showToast({
            title: '获取票据信息失败',
            icon: 'none',
          })
        }
      },
      fail: res => {
        //调用服务端登录接口失败
        wx.showToast({
          title: '服务器连接错误',
          icon: 'none',
        })
      }
    })
  }
})