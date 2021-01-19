hey

Backend Server running:
In the terminal type, `node index.js`

ssh -L 3000:localhost:3000 -L 27017:localhost:27017 -i "dylanKeyPair.pem" ubuntu@ec2-3-16-21-143.us-east-2.compute.amazonaws.com

Mongo: use {databaseName}; show collections; db.{collectionName};

GitHub issues:
Use the following command if pushing isn't working:
git gc --prune=now