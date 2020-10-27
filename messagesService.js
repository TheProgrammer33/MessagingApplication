const query = require('url');
const MongoClient = require("mongodb").MongoClient;

module.exports = function(app, url){
    
    app.get('/api/thread/:threadId/messages', function(req, res) {
        var data = query.parse(req.url, true).query;
        var threadId = req.params.threadId;

        MongoClient.connect(url, function(err, client) {
               if (err) throw err;
               var db = client.db('threads');

               db.collection("thread" + threadId).find({}).toArray(function (err, result) {
                        if (err) throw err;
                        res.send(result);
               });

               client.close();
       });

    });

    app.post('/api/thread/:threadId/message/add', function(req, res) {
            var messageBody = req.body.messageBody;
            var threadId = req.params.threadId;
            var user = req.body.user;
            messageData = {
                    messageBody: messageBody,
                    user: user,
                    messageSentDate: new Date(),
                    isEdited: false
            };
            
            console.info("Message added in ", threadId);

            MongoClient.connect(url, function(err, client) {
                    if (err) throw err;
                    var db = client.db('threads');

                    db.collection("thread" + threadId).insertOne(messageData, function (err, result) {
                            if (err) throw err;
                            res.status(200).send(result);
                    });

                    client.close();
            });
    });


}