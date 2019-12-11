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
      url: app.globalData.serverIp + '/user/events/detail?eventid=' + ticketId,
      success: res => {
        console.log(res)
        if (res.data.status == 200) {
          let ticketInfo = res.data.data
          ticketInfo.imgPath = app.globalData.serverIp + ticketInfo.imgPath
          this.setData({
            ticketInfo: ticketInfo
          })
          if (this.data.ticketInfo.ticketsLeft) {
            this.setData({
              ticketsLeft: true
            })
          }
          console.log('event detail:')
          console.log(this.data.ticketInfo)
        } else {
          wx.showToast({
            title: '获取活动信息失败',
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