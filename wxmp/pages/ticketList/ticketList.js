const app = getApp()

Page({
  data: {
    ticketList: [],
    gotTicketList: false,
  },

  jumpBtn: function(e) {
    wx.navigateTo({
      url: '../detail/detail?id=' + e.currentTarget.dataset.eventid
    })
    console.log(e)
  },

  onLoad: function(options) {
    wx.request({
      url: app.globalData.serverIp + '/user/tickets/list?skey=' +
        wx.getStorageSync('skey') + '&validation=1', // 待修改，0表示过期票，1表示显示有效票，2表示所有票（2未实现）
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: res => {
        let ticketList = res.data.data
        let len = ticketList.length
        for (let i = 0; i < len; i++) {
          ticketList[i].imgPath = app.globalData.serverIp + ticketList[i].imgPath
        }
        this.setData({
          ticketList: res.data.data,
          gotTicketList: true
        })
        console.log('ticket list:')
        console.log(this.data.ticketList)
      }
    })
  },
})