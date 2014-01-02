var SFCallback = function(){	
}
SFCallback.prototype.callback = function(){
	console.log('hello callback in nodejs');
}


var SFApp = function(){	
};

SFApp.prototype.start = function(message, callback){
	console.log(message)
    
    if (callback && typeof(callback) === "function") {
        callback();
    }
}

var app = new SFApp();
app.start('saltfactory callback demo', new SFCallback().callback);


// function callback(){
//     console.log('hello callback in javascript');
// }


// SFApp.prototype.log = function(message, callback){
// 	if (callback && typeof(callback) === "function") {
// 		console.log(message);
// 		callback();
// 	};
// }
// 
// 
// 
// 
// var app = new SFApp();
// // app.start();
// // app.log('start', function(){ console.log('test')})
// app.log('start', new SFCallback().callback);