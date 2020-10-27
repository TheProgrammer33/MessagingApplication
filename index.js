const express = require("express");
const app = express();
const url = "mongodb://localhost:27017"
const fs = require('fs');
const https = require('https');
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false }));

app.get("/", function(req, res) {
        res.send("Welcome to Catherine Gallaher's Site! :)");
});

require('./messagesService')(app, url);
require('./userService')(app, url);
require('./websocketServer');



// Start the Server
const privateKey = fs.readFileSync('/etc/letsencrypt/live/catherinegallaher.com/privkey.pem', 'utf8');
const certificate = fs.readFileSync('/etc/letsencrypt/live/catherinegallaher.com/cert.pem', 'utf8');
const ca = fs.readFileSync('/etc/letsencrypt/live/catherinegallaher.com/chain.pem', 'utf8');

const credentials = {
        key: privateKey,
        cert: certificate,
        ca: ca
};
const httpsServer = https.createServer(credentials, app);

httpsServer.listen(443, function() {
        console.log('Express app start on port 443');
});