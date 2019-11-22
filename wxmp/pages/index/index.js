//index.js
//获取应用实例

Page({
  data: {
    imgList: [],

    imgUrls: [
      'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1623318287,3864173199&fm=27&gp=0.jpg',
      'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1623318287,3864173199&fm=27&gp=0.jpg'
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 500,

  },
  jumpBtn:function(options){
    wx.navigateTo({
      url: '../detail/detail'
    })
  },
  onLoad: function (options) {
    wx.request({
      url: 'http://localhost:8000/user/events',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      },
      success: res => {
        console.log(res)
        let img = 'imgList[' + i + '].img'
        this.setData({
          [img]: res.data.data.imgPath,
        })
      },
      fail: function (error) {
        //调用服务端登录接口失败
        console.log(error);
      }
    })
    
      // wx.request({
      //   url: 'https://img.xjh.me/random_img.php',
      //   headers: {
      //     'Content-Type': 'application/json'
      //   },
      //   data: {
      //     return: 'json',
      //     type: 'bg',
      //     ctype: 'acg'
      //   },
      //   success: res => {
      //     let img = 'imgList[' + i + '].img'
      //     this.setData({
      //       [img]: res.data.data.imgPath,
      //     })
      //   }
      // })

    console.log(this.data.imgList)
  },


  GetEvent: function () {

    wx.request({
      url: '/user/events',
      method: 'GET',
      success: res => {
        console.log(res)
      },
      fail: function (error) {
        //调用服务端登录接口失败
        console.log(error);
      }
    })

  }
  
})
