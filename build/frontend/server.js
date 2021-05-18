const fetch = require('node-fetch');
var nodeBase64 = require('nodejs-base64-converter');
const express = require('express');
const ip = require('ip');
const fs = require('fs');
const http = require('http');


const app = express();

var downloadLink = {}

const PORT = 80;

app.use(express.json());


app.use(express.static('./public'));
app.use(express.static('./public_res'));
app.use(express.static('./node_modules'));

app.post("/post", (req, res) => {
    if(req.body.sd4f64sd4fs654ds == "sf46fsd4sd544sfs485dsf4f6d48sfd64s"){       
        var quality = parseFloat(req.body.sdgsfsdg654646ds)/1000;
        if(quality <= 0)
        quality = 0.01;
        else if(quality > 1)
        quality = 1;
        fetch('http://' + ip.address() + ':8080/screenshot/' +  encodeURI(quality))
        .then(resp => resp.text())
        .then(body => res.send(body));
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
        
        console.log(encodeURI(x) + "/" + encodeURI(y) + "/" + encodeURI(w) + "/" + encodeURI(h));
        
        callback = function(response) {
            var str = '';
            response.on('data', function (chunk) {
                str += chunk;
            });
            response.on('end', function () {
                res.send(str);
            });
        }
        //http.request(options, callback).end(); 
    }else if(req.body.sdf4hgfgfsgsgdg4 == "dsg4dfsdf45f4ds5f4d5fdd5fd45sf4d4f"){
        const x =  req.body.s41gd5f4g5d4g54f;
        const y =  req.body.sfds45ds45d4d4sd;
        var options = {
            host: ip.address(),
            port: 8080,
            path: "/mousemove/" + encodeURI(x) + "/" + encodeURI(y) 
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
        http.request(options, callback).send(); 
    }else if(req.body.ssd4f6ds4g5df4gd == "d4465gfds456g4f56fd4sg5f64sgfd84f6"){
        const command =  req.body.sd4fdf4g4g54ffff;
        var options = {
            host: ip.address(),
            port: 8080,
            path: "/shell/" + encodeURI(command) 
        };
        
        callback = function(response) {
            str = "";
            response.on('data', function (chunk) {
                str += chunk;
            });
            response.on('end', function () {
                res.json(JSON.parse(str));
            });
        }
        http.request(options, callback).end(); 
    }else if(req.body.defpath == "true"){
        fetch('http://' + ip.address() + ':8080/fsview/' +  nodeBase64.encode("defpath"))
        .then(resp => resp.text())
        .then(body => res.send(body));
    }else if(req.body.pathdetails == "true"){ 
        var dpath = req.body.dpath;
        fetch('http://' + ip.address() + ':8080/fsview/' +  nodeBase64.encode("getpathdetails/" + nodeBase64.encode(dpath)))
        .then(resp => resp.text())
        .then((body) => {
            res.status(200).send(body);
        });
    }else if(req.body.requestDownload == "true"){ 
        var dpath = req.body.dpath;
        var uid = generateUniqueId(64);
        downloadLink[uid] = dpath;
        res.send("http://" + ip.address() + ":" + PORT +"/download/" + uid);
    }
});

app.get('/download/:uid', (req, res)=>{
    if(downloadLink[req.params.uid] != undefined){
        console.log(downloadLink[req.params.uid]);
        if(fs.existsSync(downloadLink[req.params.uid])){
            res.sendFile(downloadLink[req.params.uid]);
        }else{
            res.status(404).send("File Not Found!");
        }
        downloadLink[req.params.uid] = undefined;
    }else{
        res.status(403).send("You are not authorised to access this site!");
    }
});


app.listen(PORT, ()=>{
    console.log("RemoteServer FrontEnd Started at http://" + ip.address() + ":" + PORT);
});

function generateUniqueId(length){
    var charset = "f4d64g6sd4g65f4gfdv1616gtuk4jllqwpjkinoine";
    var uid = "";
    for(var i=0;i<length;i++){
        uid += charset.charAt( parseInt(Math.random() * charset.length) );
    }
    return "uniqueid_" + uid;
}