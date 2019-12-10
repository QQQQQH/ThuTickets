const app = getApp()

Page({
  data: {
    eventList: [],
    gotEventList: false,
  },

  jumpBtn: function(e) {
    wx.navigateTo({
      url: '../detail/detail?id=' + e.currentTarget.dataset.eventid
    })
    console.log(e)
  },

  onLoad: function(options) {
    wx.request({
      url: app.globalData.serverIp + '/user/events/list',
      headers: {
        'Content-Type': 'application/json'
      },
      success: res => {
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
      }
    })
  },
})