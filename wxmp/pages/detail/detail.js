// pages/detail/detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //活动详情
    picInfo:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //  console.log(options)
    let picId=options.id+'.jpg';
    // this.getCurrentpic(picId);
  },
// getCurrentpic(picId)
// {
//   let that=this;
//   wx.request({
//     url: 'http://140.143.129.182:80/images/?id='+picId,
//     success(res) {
//       console.log(res);
//       if(res.data.code==0)
//       {
//         that.setData({
//           picInfo:res.data.data.picInfo
//         })

//       }
//     }
//   })
// },
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
  //简介展开
    handleExtensiontap: function (event) {
    var readyData = {
      "showAllDesc": true
    };
    this.setData(readyData);
  },
  bindPoster: function (event) {
    var posterUrl = event.currentTarget.dataset.posterUrl;
    wx.navigateTo({
      url: '/pages/detail/de' 
    });
  },
})