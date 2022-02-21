const fs = require('fs');
const express = require("express");
const async = require("async");
const path = require('path');
const bodyParser = require('body-parser')
const port = 5500;
const app = express();
const database = require('./mysql');
const { resolve } = require('path/posix');
var server = new database();
app.set('view engine', 'ejs');

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false })

app.use('/css', express.static(path.join( 'node_modules/bootstrap/dist/css')));
app.use('/js', express.static(path.join('node_modules/bootstrap/dist/js')));
app.use('/js', express.static(path.join('node_modules/jquery/dist')));

app.post('/views/index.ejs', urlencodedParser, async function (req, res) {
   // console.log(Object.values(req.body.options)); // key , values
    if(Object.keys(req.body).length == 0 )
        return ;
    else{
        var options = req.body.options.toString();
        var separator = ",";
        var string = options.split(separator);
        var program = ["JS","Python","PHP","C\#","none"];
        var result = [];
        for(let i = 0 ; i < string.length ; i++){
            console.log(string[i]);
            server.sql = "  INSERT INTO Programming(Language) VALUES (\"" + string[i] + "\")";
            server.insertData();
        }
        for(let i = 0 ; i < program.length; i++){
            server.sql = "  SELECT COUNT(*) as " + program[i].toString() + " FROM Programming WHERE Language = \"" + program[i].toString() + "\"";
            result[i] = await server.getCountingData();
            result[i] = parseInt(Object.values(result[i][0]));
            console.log(result[i]);
        }
            res.render('result.ejs',{
                Message: result
            }); // send the result from MYSQL
        }
        
    }
);

app.get('/', function(req , res){
    res.render('index');
});

app.listen( port , function(error){
    if(error){
        console.log('Something Wrong', error);
    }
    else
        console.log('Server is listening on port ' + port);
});