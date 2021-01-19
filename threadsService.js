const MongoClient = require("mongodb").MongoClient;
const mongoose = require('mongoose');

const threadSchema = new mongoose.Schema({
        threadId: Number,
        name: String,
        users: Array
});
const Thread = mongoose.model('Thread', threadSchema);

module.exports = function(app, url){
    
    app.post('/api/create-thread', createThread(req, res));
    app.get('/api/thread/:threadId/messages', getMessages(req, res));
    app.post('/api/add/users', addUsers(req, res));
    
    
    async function createThread(req, res) {
        MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
            if (err) throw err;
            var db = client.db('threads');
            
            var lastThread = await db.collection("threads").findOne({}, {sort: {threadId:-1}});
            req.body.threadId = lastThread.threadId + 1;
            if (req.body.users) {
                if (!req.body.name) {
                    req.body.name = req.body.users.replace(/,/g, ', ');
                }
                req.body.users = req.body.users.split(",");
                addThreadToUsers(req.body.users, req.body.threadId, client, req.body.name);
                var newThread = new Thread(req.body);

                var result = await db.collection("threads").insertOne(newThread);
                res.status(200).send(result.ops);
            }
            else {
                res.status(200).send(errorMessage("no users in thread", 6));
            }

            client.close();
        });
    };
    
    function getMessages(req, res) {
        var threadId = req.params.threadId;

        MongoClient.connect(url, {useUnifiedTopology: true}, function(err, client) {
            if (err) throw err;
            var db = client.db('threads');

            db.collection("threads").findOne( {threadId: parseInt(threadId)},
            function (err, result) {
                if (err) throw err;
                // For later implementation when Frontend adds functionality
                //let offset = (req.body.offset > 0 ? req.body.offset : 0);
                //let messages = result.messages.slice(offset, offset+20);
                let messages = result.messages;
                res.status(200).send(messages);
                client.close();
            });
        });
    };
    
    async function addUsers(req, res) {
        MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
            if (err) throw err;
            var db = client.db('threads');

            if (req.body.users) {
                req.body.users = req.body.users.split(",");
                addUsersToThread(req.body.users, req.body.threadId, client);
                addThreadToUsers(req.body.users, req.body.threadId, client);

                res.status(200).send({});
            }
            else {
                res.status(200).send(errorMessage("Users required to add Users", 7));
            }

            client.close();
        });
    };


    async function addThreadToUsers(users, threadId, client, name) {
        var db = client.db('users');

        users.forEach(async function(user){
            await db.collection("users").updateOne({username: user}, {$push: {threads: {threadId: threadId, name: name}}});
        });
    }
    async function addUsersToThread(users, threadId, client) {
        var db = client.db('threads');

        users.forEach(async function(user){
            await db.collection("threads").updateOne({threadId: threadId}, {$push: {users: user}});
        });
    }
}

function errorMessage(userMessage, errorCode) {
    return {
            userMessage: userMessage,
            errorCode: errorCode
    };
}