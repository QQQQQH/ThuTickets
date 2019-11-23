//index.js
//获取应用实例

Page({
  data: {
    imgList: [],
    eventList: [],

    imgUrls: [
      'http://140.143.129.182:80/images/2d5794dd-410e-420d-bc7b-2348dfdeca60.jpg',
      'http://140.143.129.182:80/images/5bb3af44-d372-4dfe-acb2-98d679f71695.jpg',
      'http://140.143.129.182:80/images/5d69ca3b-f9d4-4b47-9070-b2ba20dee510.jpg',
      'http://140.143.129.182:80/images/9e3df468-8df1-440f-92b9-9dc5908fc0de.jpg',
      'http://140.143.129.182:80/images/c6e904aa-6664-46d4-9eec-1b85a34eb6d3.jpg',
      'http://140.143.129.182:80/images/e3bec6ba-0d48-4db7-bffc-c1dc2cf14edc.jpg'
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
      url: 'http://140.143.129.182:80/user/events',
      headers: {
        'Content-Type': 'application/json'
      },

      success: res => {

        this.setData({
          eventList: res.data.data,
        })
        console.log(this.data.eventList)
        for (let i = 0; i < this.data.eventList.length; i++) {
          // console.log('140.143.129.182' + this.data.eventList[i].imgPath)
          let s = 'eventList[' + i + '].imgPath'
          let path = 'http://140.143.129.182:80' + this.data.eventList[i].imgPath
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