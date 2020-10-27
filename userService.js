const crypto = require('crypto');
const MongoClient = require("mongodb").MongoClient;

module.exports = function(app, url){
    
    app.post('/api/create-account', async function(req, res) {
        var email = req.body.email;
        var username = req.body.username;
        var password = hashPassword(req.body.password);
        var newUser = {
                email: email,
                username: username,
                password: password
        };

        MongoClient.connect(url, function(err, client) {
                if (err) throw err;
                var db = client.db('users');

                db.collection("users").findOne({ $or: [{username: username}, {email: email}] }, function(err, user) {
                        if (err) throw err;
                        if (user) {
                                if (user.username === username) {
                                        res.status(409).send(errorMessage("Username already exists", 409))
                                } else if (user.email === email) {
                                        res.status(409).send(errorMessage("Email already exists", 409))
                                } else {
                                        res.status(400).send(errorMessage("Unexpected error occured", 400))
                                }
                        } else {
                                db.collection("users").insertOne(newUser, function(err, result) { 
                                        if (err) throw err;
                                        res.status(200).send({});
                                });
                        }
        
                        client.close();
                });
                
        });
    });

    app.post('/api/login', function(req, res) {
            var username = req.body.username;
            var password = hashPassword(req.body.password);

            MongoClient.connect(url, function(err, client) {
                    if (err) throw err;
                    var db = client.db('users');
                    var successfulLogin = false;

                    db.collection("users").findOne({username: username}, function (err, result) {
                            if (err) throw err;
                            if (!result) {
                                    res.status(404).send(errorMessage("Username does not exist", 404));
                            }
                            else if (result.password !== password) {
                                    res.status(401).send(errorMessage("Incorrect password", 401));
                            }
                            else {
                                    res.status(200).send({});
                                    //TODO - create sessionId
                            }
                    });
                    client.close();
            });
    });

    function hashPassword(password) {
            return crypto.createHash('sha256').update(password).digest('base64');
    }

    function errorMessage(userMessage, errorCode) {
            return {
                    userMessage: userMessage,
                    errorCode: errorCode
            }
    }


}
