const mysql = require("mysql2");

const db = mysql.createConnection({
host: "localhost",
user: "root",
password: "Jahnavi@08",
database: "inventory_db"
});

db.connect(err => {
if(err){
console.log("DB Error:", err);
}else{
console.log("MySQL Connected");
}
});

module.exports = db;