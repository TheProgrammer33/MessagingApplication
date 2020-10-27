const fs = require('fs');
const https = require('https');

module.exports = function(app) {
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
}