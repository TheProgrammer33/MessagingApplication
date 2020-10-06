var express = require("express");
var query = require('url');
var MongoClient = require("mongodb").MongoClient;
var events = require('events');
var app = express();
var url = "mongodb://localhost:27017/";
var eventEmitter = new events.EventEmitter();

var newMessage = function(threadId) {
  //send out new messages to users with app open
}
eventEmitter.on('newMessage', newMessage);

app.get("/", function(req, res) {
  res.send("Pong");
});

app.get('/api/thread/:threadId/messages', function(req, res) {
  var threadId = req.params.threadId;
  var userId = req.query.userId;
  //TODO - check if userId can view messages in the thread

  MongoClient.connect(url, function(err, client) {
    if (err) throw err;
    var db = client.db('Threads');
    
    db.collection(threadId).find({}).toArray(function (err, result) {
      if (err) throw err;
      res.send(result);
    });

    client.close();
  });

});

app.post('/api/thread/:threadId/message/add', function(req, res) {
  var threadId = req.params.threadId;
  var messageData = req.query;
  var userId = messageData.userId;
  //TODO - check if userId can view messages in the thread
  //eventEmitter.emit('newMessage');

  MongoClient.connect(url, function(err, client) {
    if (err) throw err;
    var db = client.db('Threads');
    
    db.collection(threadId).insertOne(messageData, function (err, result) {
      if (err) throw err;
      res.send(result);
    });

    client.close();
  });
});

app.post('/api/create-account', function(req, res) {
  var userName = req.query.userName;
  var password = req.query.password;
  //TODO - create user id and hash password

  MongoClient.connect(url, function(err, client) {
    if (err) throw err;
    var db = client.db('Users');
    
    db.collection(userId).insertOne(messageData, function (err, result) {
      if (err) throw err;
      response.send(result);
    });

    client.close();
  });
});

app.listen(3000, function(){
  console.log('Express app start on port 3000')
});


/*
Base Mongo Connection

  MongoClient.connect(url, function(err, client) {
    if (err) throw err;
    var db = client.db();
    
    db.collection().(function (err, result) {
      if (err) throw err;
      response.send();
    });

    client.close();
  });

*/