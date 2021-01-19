const express = require("express");
const app = express();
const url = "mongodb://localhost:27017"
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false }));

app.get("/", function(req, res) {
        res.send("Welcome to Catherine Gallaher's Site! :)");
});

require('./server')(app, url);

require('./userService')(app, url);
require('./friendService')(app, url);
require('./threadsService')(app, url);
require('./mongoService')();
