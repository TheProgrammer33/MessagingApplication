const crypto = require('crypto');
const MongoClient = require("mongodb").MongoClient;
const ObjectId = require("mongodb").ObjectId;
const mongoose = require('mongoose');

const friendSchema = new mongoose.Schema({
        username: String
});
const Friends = mongoose.model('Friends', friendSchema);

module.exports = function(app, url) {
    
    app.post('/api/friends', getFriends(req, res));
    app.post('/api/add-friend', addFriend(req, res));
    app.put('/api/remove-friend', removeFriend(req, res));


    async function getFriends(req, res) {

        MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                if (err) throw err;
                var db = client.db('users');

                var result = await db.collection("users").findOne({_id: ObjectId(req.body.sessionId)});
                if (!result) {
                    res.status(200).send({friends: []});
                } else {
                    res.status(200).send({friends: result.friends});
                }

                client.close();
        });
    };
    
    function addFriend(req, res) {
            var newFriend = new Friends(req.body);

            MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                    if (err) throw err;
                    var db = client.db('users');

                    var result = await db.collection("users").findOne({username: req.body.username});
                    if (!result) {
                        res.status(200).send(errorMessage("Username does not exist", 1));
                    }
                    else {
                        try {
                            await db.collection("users").updateOne({_id: ObjectId(req.body.sessionId)}, {$push: {friends: newFriend}});
                            res.status(200).send({});
                        } catch(error) {
                            console.info(error);
                            res.status(200).send(errorMessage("Invalid sessionId", 4));
                        }
                    }

                    client.close();
            });
    };
    
    function removeFriend(req, res) {
        
        MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                if (err) throw err;
                var db = client.db('users');

                var result = await db.collection("users").findOne({username: req.body.username});
                if (!result) {
                    res.status(200).send(errorMessage("Username does not exist", 1));
                }
                else {
                    await db.collection("users").updateOne({_id: ObjectId(req.body.sessionId)}, {$pull: {friends: {username: req.body.username}}});
                    res.status(200).send({});
                }

                client.close();
        });
    };

}

function errorMessage(userMessage, errorCode) {
    return {
            userMessage: userMessage,
            errorCode: errorCode
    }
}