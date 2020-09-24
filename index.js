var express = require("express");  
var app = express();  
var MongoClient = require("mongodb").MongoClient;
var url = "mongodb://localhost:27017/mydb";

app.get("/", function(req, res) {  
  res.send("Hello World!");  
});  

app.get("/users", function() {  
  MongoClient.connect(url, function(err, db) {  
    if (err) {console.info("An error Occured");}
    console.log("Database created!");
    db.close(); 
  });  
});

app.listen(3000,function(){  
    console.log('Express app start on port 3000')  
});