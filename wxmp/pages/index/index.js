//index.js
//获取应用实例

Page({
  data: {
    imgList: [],
    eventList: [],

    imgUrls: [
      'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1623318287,3864173199&fm=27&gp=0.jpg',
      'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1623318287,3864173199&fm=27&gp=0.jpg'
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 500,

  },
  jumpBtn: function(options) {
    wx.navigateTo({
      url: '../detail/detail'
    })
  },
  onLoad: function(options) {

    wx.request({
      url: 'http://183.173.152.189:8000/user/events',
      headers: {
        'Content-Type': 'application/json'
      },

      success: res => {

        this.setData({
          eventList: res.data.data,
        })
        console.log(this.data.eventList)
        for (let i = 0; i < this.data.eventList.length; i++) {
          console.log('http://183.173.152.189:8000' + this.data.eventList[i].imgPath)
          let s = 'eventList[' + i + '].imgPath'
          let path = 'http://183.173.152.189:8000' + this.data.eventList[i].imgPath
          this.setData({
            [s]:path
          })
        }

        console.log(this.data.eventList)
      }
    })
    console.log(this.data.imgList)
  },




})