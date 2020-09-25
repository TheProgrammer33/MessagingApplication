var express = require("express");
var app = express();
var query = require('url');
var MongoClient = require("mongodb").MongoClient;
var url = "mongodb://localhost:27017/";

app.get("/", function(req, res) {
  res.send("Pong");
});

app.get("/messages", function(req, res) {
  console.info("Start the thing here!@#$%^&*(): ", query.parse(req.url, true).query);
  getFromDatabase(req, 'Messages', res)
});

app.get("/messages/add", function(req, res) {
  data = query.parse(req.url, true).query
  if (!data.user || !data.message) {
    res.send(invalidData());
  }

  setToDatabase(data, 'Messages', res);
});

app.listen(3000,function(){
  console.log('Express app start on port 3000')
});

function getFromDatabase(data, collection, response) {
  MongoClient.connect("mongodb://localhost:27017/", function (err, client) {
    var db = client.db('testDB');

    db.collection(collection).find({}).toArray(function (err, result) {
            if (err) throw err;
            response.send(result);
    });
    client.close();
  });
}

function setToDatabase(data, collection, response) {
  MongoClient.connect("mongodb://localhost:27017/", function (err, client) {
    var db = client.db("testDB");

    console.info(data);
    db.collection(collection).insertOne(data, function (err, result) {
            if (err) throw err;
            console.info("Data Saved");
            response.send(result.ops);
    });
    client.close();
  });

}

function invalidData() {
  return "400: Bad Request"
}