const app = getApp()

Page({
  data: {
    ticketList: [],
    gotTicketList: false,
  },

  onLoad: function(options) {
    this.refreshPage()
  },

  onPullDownRefresh: function() {
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
        console.log(res)
        if (res.data.status == 200) {
          let ticketList = res.data.data
          let len = ticketList.length
          for (let i = 0; i < len; i++) {
            ticketList[i].imgPath = app.globalData.serverIp + ticketList[i].imgPath
          }
          this.setData({
            ticketList: res.data.data,
          })
          if (len > 0) {
            this.setData({
              gotTicketList: true
            })
          }
          console.log('ticket list:')
          console.log(this.data.ticketList)
        } else {
          wx.showToast({
            title: '获取票据失败',
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