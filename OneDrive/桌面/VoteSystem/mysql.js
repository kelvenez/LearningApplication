const { start } = require("repl");
const async = require("async");
const { query } = require("express");
const { count } = require("console");
const mysql = require("mysql2");
const connection = mysql.createConnection(
  {
    host: 'localhost',
    port: '3306',
    database: 'Vote',
    user: 'root',
    password: '60770099'
  });




class Database{
  constructor(){
    connection.connect();
    this.sql = '';
    console.log("Sucessful");
  }

  ShowData(){
      this.sql = 'SELECT * FROM Programming';
      connection.query(this.sql,function (err, result) {
        if(err){
          console.log('[SELECT ERROR] - ',err.message);
          return;
        }
      console.log('--------------------------SELECT----------------------------');
      console.log(result);
      console.log('------------------------------------------------------------\n\n');  
  })};

   sendRequest(sql){
    let promiseQuery = new Promise(function(resolve,reject){
         connection.query(sql, (err,result)=>{
            if(err) reject(err);
            resolve(result);
         })});
    return promiseQuery;
  };

  async getCountingData(){
      let data = await this.sendRequest(this.sql);
      return data;
  };

  insertData(){
    connection.query(this.sql, function (err, result)
    { 
      if(err){ 
        console.log("Error");
        return;
      }
    })
  }; 


  start(){
    connection.connect();
  };

  end(){
    connection.end();
  };
}
module.exports = Database;