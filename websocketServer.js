const MongoClient = require("mongodb").MongoClient;
const mongoose = require('mongoose');

const messagesSchema = new mongoose.Schema({
        user: String,
        messageBody: String,
        messageSentDate: Date,
        isEdited: Boolean
});
const Message = mongoose.model('Message', messagesSchema);

const WebSocketServer = require('ws');

module.exports = function(httpsServer, url){

    const wss = new WebSocketServer.Server({server: httpsServer});

    wss.on('connection', (ws, req) => {
        ws.send('Websocket Connected');
        console.info("you connected boi")

        ws.on('message', (data) => {
            saveMessage(JSON.parse(data), url);
            sendNewMessageAlert(wss, JSON.parse(data));
        });
        ws.on('close', () => {
            console.log('socket closed');
        });
    });
}

function saveMessage(data, url) {
    var threadId = data.threadId;
    var newMessageData = {
        user: data.user,
        messageBody: data.message,
        messageSentDate: new Date(),
        isEdited: false
    }
    messageData = new Message(newMessageData);

    MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
        if (err) throw err;
        var db = client.db('threads');

        var result = await db.collection("threads").updateOne({threadId: parseInt(threadId)}, {$push: {messages: messageData}});

        client.close();
    });
}

function sendNewMessageAlert(wss, data) {
    wss.clients.forEach(function(client) {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify({update: "New messages", message: data.message, threadId: data.threadId, user: data.user}));
        }
    });
}

//https://github.com/websockets/ws