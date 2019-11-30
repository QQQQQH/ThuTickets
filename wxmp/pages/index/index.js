const app = getApp()

Page({
  data: {
    eventList: [],
    gotEventList: false,
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 500,
  },

  jumpBtn: function(e) {
    wx.navigateTo({
      url: '../detail/detail?id=' + e.currentTarget.dataset.eventid
    })
    console.log(e)
  },

  onLoad: function(options) {
    let url = app.globalData.serverIp + '/user/events'
    wx.request({
      url: url,
      headers: {
        'Content-Type': 'application/json'
      },
      success: res => {
        this.setData({
          eventList: res.data.data,
        })
        // console.log(this.data.eventList)
        for (let i = 0; i < this.data.eventList.length; i++) {
          let s = 'eventList[' + i + '].imgPath'
          let path = app.globalData.serverIp + this.data.eventList[i].imgPath
          this.setData({
            [s]: path
          })
        }
        this.setData({
          gotEventList: true
        })
        console.log(this.data.eventList)


      }
    })
  },
})