const crypto = require('crypto');
const MongoClient = require("mongodb").MongoClient;
const mongoose = require('mongoose');
const ObjectId = require("mongodb").ObjectId;

const userSchema = new mongoose.Schema({
        email: String,
        username: String,
        password: String,
        friends: Array,
        threads: Array,
        notifications: Boolean
});
const User = mongoose.model('User', userSchema);

module.exports = function(app, url){        

        app.post('/api/create-account', async function(req, res) {
                let username = req.body.username;
                let email = req.body.email;
                req.body.password = hashPassword(req.body.password);
                req.body.notifications = true;
                var newUser = new User(req.body);

                MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                        if (err) throw err;
                        var db = client.db('users');

                        var user = await db.collection("users").findOne({ $or: [{username: username}, {email: email}] });

                        if (user) {
                                if (user.username === username) {
                                        res.status(200).send(errorMessage("Username already exists", 3));
                                } else if (user.email === email) {
                                        res.status(200).send(errorMessage("Email already exists", 3));
                                } else {
                                        res.status(200).send(errorMessage("Unexpected error occured", 99));
                                }
                        } else {
                                var result = await db.collection("users").insertOne(newUser);
                                res.status(200).send({});
                        }

                        client.close();

                });
        });
        
        app.post('/api/login', function(req, res) {
                var username = req.body.username;
                var password = hashPassword(req.body.password);

                MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                        var db = client.db('users');

                        var result = await db.collection("users").findOne({username: username});
                        if (!result) {
                                res.status(200).send(errorMessage("Username does not exist", 1));
                        }
                        else if (result.password === password) {
                                delete result.password;
                                res.status(200).send(result);
                        }
                        else {
                                res.status(200).send(errorMessage("Incorrect password", 2));
                        }
                });
        });
        
        app.get('/api/get-user', function(req, res) {
                var username = req.body.username;
                MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                        if (err) throw err;
                        var db = client.db('users');

                        var result = await db.collection("users").findOne({username: username});
                        if (result) {
                                res.status(200).send({});
                        }
                        else {
                                res.status(200).send(errorMessage("Username does not exist", 1));
                        }
                        res.status(200).send({});

                        client.close();
                });
        });
        
        app.post('/api/change-password', function(req, res) {
                MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                        if (err) throw err;
                        var db = client.db('users');

                        try {
                                var result = await db.collection("users").findOne({_id: ObjectId(req.body.sessionId)});
                                if (!result) {
                                        res.status(200).send(errorMessage("Invalid sessionId", 4));
                                }
                                else if (result["password"] !== hashPassword(req.body.password)) {
                                        res.status(200).send(errorMessage("Incorrect password", 1));
                                }
                                else if (hashPassword(req.body.newPassword) !== result["password"]){
                                        await db.collection("users").updateOne({_id: ObjectId(req.body.sessionId)}, { $set:{password: hashPassword(req.body.newPassword)}});
                                        res.status(200).send({});
                                }
                                else {
                                        res.status(200).send(errorMessage("Password cannot be the same as your old password", 5));
                                }
                        } catch (error) {
                                console.info(error);
                                res.status(200).send(errorMessage("Invalid sessionId", 4));
                        }

                        client.close();
                });
        });
        
        app.post('/api/change-email', function(req, res) {
                MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                        if (err) throw err;
                        var db = client.db('users');

                        try {
                                var result = await db.collection("users").findOne({_id: ObjectId(req.body.sessionId)});
                                if (!result) {
                                        res.status(200).send(errorMessage("Invalid sessionId", 4));
                                }
                                else if (req.body.newEmail !== result["email"]){
                                        await db.collection("users").updateOne({_id: ObjectId(req.body.sessionId)}, { $set: {email: req.body.newEmail}});
                                        res.status(200).send({});
                                }
                                else {
                                        res.status(200).send(errorMessage("Email cannot be the same as your old email", 5));
                                }
                        } catch (error) {
                                console.log(error);
                                res.status(200).send(errorMessage("Invalid sessionId", 4));
                        }

                        client.close();
                });
        });

        app.post('/api/settings/save', function(req, res) {
                if (req.body.notifications) {
                        var notifications = (req.body.notifications == 'true');
                }
                MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                        if (err) throw err;
                        var db = client.db('users');
                        
                        try {
                                var result = await db.collection("users").updateOne({_id: ObjectId(req.body.sessionId)}, {$set: {notifications: notifications}});
                                if (!result) {
                                        res.status(200).send(errorMessage("Invalid sessionId", 4));
                                }
                                else {
                                        res.status(200).send({});
                                }
                        } catch (error) {
                                console.log(error);
                                res.status(200).send(errorMessage("Invalid sessionId", 4));
                        }

                        client.close();                
                });
        });
        
        app.post('/api/settings', function(req, res) {
                MongoClient.connect(url, {useUnifiedTopology: true}, async function(err, client) {
                        if (err) throw err;
                        var db = client.db('users');
                        
                        try {
                                var result = await db.collection("users").findOne({_id: ObjectId(req.body.sessionId)});
                                if (!result) {
                                        res.status(200).send(errorMessage("Invalid sessionId", 4));
                                } else {
                                        console.info(result);
                                        res.status(200).send({notifications: (result.notifications ? true : false)});
                                }
                        } catch (error) {
                                console.log(error);
                                res.status(200).send(errorMessage("Invalid sessionId", 4));
                        }

                        client.close();                
                });
        });

}

function hashPassword(password) {
        return crypto.createHash('sha256').update(password).digest('base64');
}

function errorMessage(userMessage, errorCode) {
        return {
                userMessage: userMessage,
                errorCode: errorCode
        }
}