const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function() {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    }
  },

  onShow: function() {
    if (app.globalData.token != null) {
      wx.request({
        url: app.globalData.serverIp + '/user/verifcation',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          token: app.globalData.token //助教小程序返回的身份验证token
        },
        success: res => {
          if (res.data.status == 200) {
            wx.getStorageSync('skey', res.data);
            wx.showToast({
              title: '绑定成功',
              duration: 1000
            })
          } else {
            console.log('服务器异常');
          }
        },
        fail: function(error) {
          //调用服务端登录接口失败
          console.log(error);
        }
      })
    }
  },

  handleClickGetUserInfo: function(e) {
    // 点击获取昵称和头像按钮
    console.log(e)
    if (e.detail.userInfo) {
      app.globalData.userInfo = e.detail.userInfo
      app.globalData.rawData = e.detail.rawData
      app.globalData.signature = e.detail.signature
      app.globalData.encrypteData = e.detail.encrypteData
      app.globalData.iv = e.detail.iv
      this.setData({
        userInfo: e.detail.userInfo,
        hasUserInfo: true
      })
      app.login()
    } else {}
  },
  handleClickBindId: function() {
    // 点击绑定学号按钮
    wx.navigateToMiniProgram({
      appId: "wx1ebe3b2266f4afe0",
      path: "pages/index/index",
      envVersion: "trial",
      extraData: {
        origin: "miniapp",
        type: "id.tsinghua"
      }
    })





  }
})