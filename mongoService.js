const crypto = require('crypto');
const MongoClient = require("mongodb").MongoClient;
const mongoose = require('mongoose');
const ObjectId = require("mongodb").ObjectId;
const mongoclient = new MongoClient("mongodb://localhost:27017", {useUnifiedTopology: true})

const userSchema = new mongoose.Schema({
        email: String,
        username: String,
        password: String,
        friends: Array,
        threads: Array,
        notifications: Boolean
});
const User = mongoose.model('User', userSchema);

module.exports = function(){
    
    //https://docs.mongodb.com/drivers/node/fundamentals/connection
    async function connectToMongo(task) {
        try {
            mongoclient.connect();
            task(mongoclient)
        }
        catch(err) {
            console.error(err);
        }
        finally {
            await mongoclient.close();
        }
    }
}