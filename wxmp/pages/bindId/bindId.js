// pages/bindId/bindId.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: '',
    id: '',
  },

  inputName: function(e) {
    // 存储姓名输入框中的数据
    this.setData({
      name: e.detail.value
    })
  },

  inputId: function(e) {
    // 存储学号输入框中的数据
    this.setData({
      id: e.detail.value
    })
  },

  popConfirm: function() {
    wx.showModal({
      title: '确认绑定',
      content: '确认要绑定学号吗？',
      success: (e) => {
        if (e.confirm) {
          console.log('点击确认');
          this.bindId();
        } else {
          console.log('点击取消');
        }
      }
    })
  },

  bindId: function() {
    // "确认绑定"点击事件
    if (this.data.name.length == 0 || this.data.id.length == 0) {
      // 若姓名或学号为空
      this.popText('姓名和学号不能为空！', 'none');
    } else {
      // 若姓名和学号不为空
      wx.request({
        url: 'http://localhost:8080/bindId',
        method: 'POST',
        data: {
          name:this.data.name,
          id:this.data.id
        },
        success: res => {
          if (res.data.status == 200) {
            this.popText('绑定成功！', '');
          } else {
            console.log('服务器异常');
            this.popText('绑定失败', '');
          }
        },
        fail: function(error) {
          //调用服务端登录接口失败
          console.log(error);
        }
      })
    }
  },

popText: function(title, icon) {
  wx.showToast({
    title: title,
    // icon默认或空显示成功提示，为none不显示
    icon: icon,
    duration: 1000
  })
},

/**
 * 生命周期函数--监听页面加载
 */
onLoad: function(options) {

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

}
})