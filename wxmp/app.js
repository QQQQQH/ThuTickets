//app.js
App({
  onLaunch: function() {
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              this.globalData.rawData = res.rawData //用户非敏感信息
              this.globalData.signature = res.signature //签名
              this.globalData.encrypteData = res.encryptedData //用户敏感信息
              this.globalData.iv = res.iv //解密算法的向量

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
              this.login()
            }
          })
        }
      }
    })
  },

  onShow: function(options) {
    // 获取清华身份验证小程序返回的token
    this.globalData.token = options.referrerInfo.extraData
  },

  login: function() {
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        console.log('wx.login success, res.code = ' + res.code)
        wx.request({
          url: this.globalData.serverIp + '/user/login',
          method: 'POST',
          header: {
            'content-type': 'application/x-www-form-urlencoded'
          },
          data: {
            code: res.code, //临时登录凭证
            rawData: this.globalData.rawData, //用户非敏感信息
            signature: this.globalData.signature, //签名
            encrypteData: this.globalData.encryptedData, //用户敏感信息
            iv: this.globalData.iv //解密算法的向量
          },
          success: res => {
            if (res.data.status == 200) {
              wx.setStorageSync('skey', res.data);
              wx.showToast({
                title: '登录成功',
                duration: 1000
              })
              console.log('login success, skey = ' + wx.getStorageSync('skey'))
            } else {
              wx.showToast({
                title: '登录失败',
                duration: 1000
              })
            }
          },
          fail: function(error) {
            //调用服务端登录接口失败
            console.log(error);
          }
        })
      }
    })
  },

  globalData: {
    userInfo: null,
    rawData: null, //用户非敏感信息
    signature: null, //签名
    encrypteData: null, //用户敏感信息
    iv: null, //解密算法的向量
    token: null,
    serverIp: 'http://140.143.129.182:80'
  }
})