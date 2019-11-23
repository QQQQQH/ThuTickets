// pages/wallet/wallet.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  },
  jumpBtn_tickets: function (options) {
    wx.navigateTo({
      url: '../tickets/tickets'
    })
  },

  //获取应用实例
    data: {
      modalHidden:true,//是否隐藏对话框
      modalHidden2:true,
    },
    //事件处理函数
    bindViewTap: function() {
    this.setData({
      modalHidden: !this.data.modalHidden,


    })

  },
  
  //确定按钮点击事件
  modalBindaconfirm: function () {
    this.setData({
      modalHidden: !this.data.modalHidden,
      modalHidden2: !this.data.modalHidden2,

    })

  },
  //确定按钮点击事件
  modalBindaconfirm2: function () {
    this.setData({
      modalHidden2: !this.data.modalHidden2,

    })

  },
  //取消按钮点击事件
  modalBindcancel: function () {
    this.setData({
      modalHidden: !this.data.modalHidden,
    })
  },
  modalBindcancel2: function () {
    this.setData({
      modalHidden2: !this.data.modalHidden2,
    })
  },
  
})