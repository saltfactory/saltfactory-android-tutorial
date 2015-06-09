var gcm = require('node-gcm');
var fs = require('fs');

var message = new gcm.Message({
    collapseKey: 'demo',
    delayWhileIdle: true,
    timeToLive: 3,
    data: {
        title: 'saltfactory GCM demo',
        message: 'Google Cloud Messaging 테스트',
        custom_key1: 'custom data1',
        custom_key2: 'custom data2'
    }
});


var server_api_key = 'Server API Key를 입력합니다.';
var sender = new gcm.Sender(server_api_key);
var registrationIds = [];

var token = 'Instance ID 의 token을 입력합니다.';
registrationIds.push(token);

sender.send(message, registrationIds, 4, function (err, result) {
    console.log(result);
});
