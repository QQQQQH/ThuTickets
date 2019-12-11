const app = getApp()
import drawQrcode from '../../utils/weapp.qrcode.esm.js'

Page({
  data: {
    eventId: null,
    ticketId: null,
    fromPage: null,
    ellipsis: true, // 文字是否收起，默认收起
    eventInfo: null, // 活动详情
    ticketsLeft: false // 有余票
  },

  onLoad: function(options) {
    this.setData({
      eventId: options.eventId,
      ticketId: options.ticketId ? options.ticketId : null
    })
    if(this.data.ticketId){
      drawQrcode({
        width: 200,
        height: 200,
        canvasId: 'myQrcode',
        text: this.data.ticketId
      })
    }
    console.log('detail: eventId=' + this.data.eventId + '; ticketId=' + this.data.ticketId)
    this.refreshPage(this.data.eventId);
  },

  onPullDownRefresh: function() {
    wx.stopPullDownRefresh()
    this.refreshPage(this.data.eventId)
  },

  refreshPage: function(eventId) {
    wx.request({
      url: app.globalData.serverIp + '/user/events/detail?eventid=' + eventId,
      success: res => {
        console.log(res)
        if (res.data.status == 200) {
          let eventInfo = res.data.data
          eventInfo.imgPath = app.globalData.serverIp + eventInfo.imgPath
          this.setData({
            eventInfo: eventInfo
          })
          if (this.data.eventInfo.ticketsLeft) {
            this.setData({
              ticketsLeft: true
            })
          }
          console.log('event detail:')
          console.log(this.data.eventInfo)
        } else {
          wx.showToast({
            title: '获取活动信息失败',
            icon: 'none',
          })
        }
      },
      fail: res => {
        //调用服务端登录接口失败
        wx.showToast({
          title: '服务器连接错误',
          icon: 'none',
        })
      }
    })
  },

  ellipsis: function() {
    //简介展开
    var value = !this.data.ellipsis;
    this.setData({
      ellipsis: value
    })
  },

  handleGetTicket: function(e) {
    wx.request({
      url: app.globalData.serverIp + '/user/buy-ticket?skey=' +
        wx.getStorageSync('skey') + '&eventid=' + this.data.eventInfo.eventid,
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: res => {
        console.log('res:')
        console.log(res);
        if (res.data.status == 200) {
          wx.showToast({
            title: '抢票成功',
          })
        } else {
          wx.showToast({
            icon: 'none',
            title: '抢票失败，请检查是否已绑定学号',
          })
        }
      },
      fail: function(error) {
        wx.showToast({
          icon: 'none',
          title: '服务器连接错误',
        })
        console.log('服务器连接错误');
      }
    })
  },

  handleReturnTicket: function(e) {
    wx.showModal({
      title: '警告',
      content: '确认要退票吗？',
      success: res => {
        if (res.confirm) {
          this.returnTicket()
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  returnTicket: function() {
    wx.request({
      url: app.globalData.serverIp + '/user/tickets/delete?skey=' + wx.getStorageSync('skey') + '&ticketid=' + this.data.ticketId,
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: res =>{
        console.log('res: ' + res)
        if(res.data.status == 200){
          wx.showToast({
            title: '退票成功',
          })
        }else{
          wx.showToast({
            title: '退票失败',
            icon: 'none',
          })
        }
      },
      fail: res =>{
        wx.showToast({
          icon: 'none',
          title: '服务器连接错误',
        })
        console.log('服务器连接错误');
      }
    })
  }
})