const app = getApp()

Page({
  data: {
    ticketList: [],
    gotTicketList: -1,
  },

  onLoad: function(options) {
    this.refreshPage()
  },

  onPullDownRefresh: function() {
    wx.stopPullDownRefresh()
    this.refreshPage()
  },

  refreshPage: function() {
    wx.request({
      url: app.globalData.serverIp + '/user/tickets/list?skey=' +
        wx.getStorageSync('skey') + '&validation=1', // 待修改，0表示过期票，1表示显示有效票，2表示所有票（2未实现）
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: res => {
        console.log('res:')
        console.log(res)
        if (res.data.status == 200) {
          let ticketList = res.data.data
          let len = ticketList.length
          // for (let i = 0; i < len; i++) {
          //   ticketList[i].imgPath = app.globalData.serverIp + ticketList[i].imgPath
          // }
          this.setData({
            ticketList: ticketList,
            gotTicketList: len > 0
          })
          console.log('ticket list:')
          console.log(this.data.ticketList)
        } else {
          wx.showToast({
            title: '获取票据失败',
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