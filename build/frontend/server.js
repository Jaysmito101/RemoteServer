const e = require('express');
const express = require('express');
const ip = require('ip');
const http = require('http');


const app = express();

const PORT = 80;

app.use(express.json());


app.use(express.static('./public'));

app.post("/post", (req, res) => {
    if(req.body.sd4f64sd4fs654ds == "sf46fsd4sd544sfs485dsf4f6d48sfd64s"){
        var options = {
            host: ip.address(),
            port: 8080,
            path: "/screenshot"
        };
        
        callback = function(response) {
            var str = '';
            response.on('data', function (chunk) {
                str += chunk;
            });
            response.on('end', function () {
                res.send(str);
            });
        }
        http.request(options, callback).end(); 
    }else if(req.body.a64f6s4df45gf4df == "df46g8df47g64g6f4dg6847gf84fd6g468"){
        const keyCode = req.body.sd4f6sd4f8f4dd4f;
        var options = {
            host: ip.address(),
            port: 8080,
            path: "/keypress/" + encodeURI(keyCode)
        };
        
        callback = function(response) {
            var str = '';
            response.on('data', function (chunk) {
                str += chunk;
            });
            response.on('end', function () {
                res.send(str);
            });
        }
        http.request(options, callback).end(); 
    }else if(req.body.ssg4f69s4fd4f6sd == "d4f68g48fd4gd68gf847g6ssfg8f4g84f6"){
        const keyCode = req.body.sd4f6sh4f8f4dd4f;
        var options = {
            host: ip.address(),
            port: 8080,
            path: "/command/" + encodeURI(keyCode)
        };
        
        callback = function(response) {
            var str = '';
            response.on('data', function (chunk) {
                str += chunk;
            });
            response.on('end', function () {
                res.send(str);
            });
        }
        http.request(options, callback).end(); 
    }else if(req.body.sd4f64hjsfs654ds == "sds54fdsdf1fdssddfgdsfd15ds4f5d64s"){
        const x =  req.body.sd4f6f15sad1yhfg;
        const y =  req.body.sd4f6fbkf4sd5f4g;
        const w =  req.body.sd4f6fbkfdgsyhfg;
        const h =  req.body.sd4gassdggf654ds;
        var options = {
            host: ip.address(),
            port: 8080,
            path: "/mouseclick/" + encodeURI(x) + "/" + encodeURI(y) + "/" + encodeURI(w) + "/" + encodeURI(h) 
        };
        
        callback = function(response) {
            var str = '';
            response.on('data', function (chunk) {
                str += chunk;
            });
            response.on('end', function () {
                res.send(str);
            });
        }
        http.request(options, callback).end(); 
    }
});


app.listen(PORT, ()=>{
    console.log("RemoteServer FrontEnd Started at http://" + ip.address() + ":" + PORT);
});