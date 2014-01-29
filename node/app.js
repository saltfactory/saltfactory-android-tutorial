var http=require('http'),
	https = require('https'),
	express = require('express'),
 	fs = require('fs');

var options = {   
	key: fs.readFileSync('key.pem'),
	cert: fs.readFileSync('cert.pem')
};


var port1 = 80;
var port2 = 443;

var app = express();
app.use(express.urlencoded());
app.use(express.logger());

http.createServer(app).listen(port1, function(){
  console.log("Express server listening on port " + port1);
});


https.createServer(options, app).listen(port2, function(){
  console.log("Express server listening on port " + port2);
});


app.get('/', function (req, res) {
	res.writeHead(200, {'Content-Type' : 'text/html'});
	res.write('<h3>Welcome</h3>');
	res.write('<a href="/login">Please login</a>');
	res.end();
});

app.get('/login', function (req, res){
	res.writeHead(200, {'Content-Type': 'text/html'});
	res.write('<h3>Login</h3>');
	res.write('<form method="POST" action="/login">');
	res.write('<label name="userId">UserId : </label>')
	res.write('<input type="text" name="userId"><br/>');
	res.write('<label name="password">Password : </label>')
	res.write('<input type="password" name="password"><br/>');
	res.write('<input type="submit" name="login" value="Login">');
	res.write('</form>');
	res.end();
})

// app.post('/login', function (req, res){
// 	var userId = req.param("userId");
// 	var password = req.param("password")
// 	
// 	res.writeHead(200, {'Content-Type': 'text/html'});
// 	res.write('Thank you, '+userId+', you are now logged in.');
// 	res.write('<p><a href="/"> back home</a>');
// 	res.end();
// });

app.post('/login', function (req, res){
		var userId = req.param("userId");
		var password = req.param("password")
	
	res.json({userId:userId, password:password});	
});
