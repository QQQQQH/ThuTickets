const app = getApp()
Page({


  /**
   * 页面的初始数据
   */
  data: {
    ellipsis: true, // 文字是否收起，默认收起
    //活动详情
    eventInfo: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let picId = options.id;
    this.getCurrenteventInfo(picId);
  },

  getCurrenteventInfo(picId) {
    wx.request({
      url: app.globalData.serverIp + '/user/events/image?id=' + picId,
      success: res => {
        this.setData({
          eventInfo: res.data.data
        })
        let s = 'eventInfo.imgPath'
        let path = app.globalData.serverIp + this.data.eventInfo.imgPath
        this.setData({
          [s]: path
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  //简介展开
  ellipsis: function() {
    var value = !this.data.ellipsis;
    this.setData({
      ellipsis: value
    })
  },
  // bindPoster: function (event) {
  //   var posterUrl = event.currentTarget.dataset.posterUrl;
  //   wx.navigateTo({
  //     url: '/pages/detail/de' 
  //   });
  // },
  is_touch: function(e) {
    let skey = wx.getStorageSync('skey').data // 登录态
    wx.request({
      url: app.globalData.serverIp + '/user/buy-ticket?skey=' + value + '&eventid=' + this.data.eventInfo.eventid,
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },

      success: res => {
        console.log(res);
      },
      fail: function(error) {
        console.log('failed');
      }
    })
  },
})