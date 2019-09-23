// pages/me/me.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    isShow: true,
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 初始化工作，发送请求，开启定时器
  },

  getUserInfo: function () {
    // 获取用户登录的信息

    // 判断用户是否授权
    wx.getSetting({
      success: (data) => {
        if (data.authSetting['scope.userInfo']) {
          //  用户已经授权
          this.setData({
            isShow: false
          });
        } else {
          //  没有授权
        }
      }
    })

    wx.getUserInfo({
      success: (data) => {
        // 更新data中的userInfo
        this.setData({
          userInfo: data.userInfo
        })
      },
      fail: () => {
        console.log('getUserInfo failed')
      }
    })
  },

  handleGetUserInfo: function (data) {
    // 判断用户是否点击允许
    if (data.detail.rawData) {
      // 用户点击允许
      this.getUserInfo();
    }
  },

  bindId: function (){
    console.log('bindId')
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})