//me.js
//获取应用实例
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
    wx.navigateTo({
      url: '../bindId/bindId'
    })
  }
})