const mysql = require('mysql');
var con = null;

function createConnection(){
    con = mysql.createConnection({
       host: "localhost",
       user: "root",
       password: "",
       database: "estoque"
    })
}

function ProductType(name){
    this.name = name;
}

ProductType.prototype.list = callback =>{
    createConnection();
    
    con.query("SELECT * FROM produtos_tipos", (err, rows) =>{
        if(err){
            callback({ error: true, msg: err});
            return;
        }
    
        callback(rows);
    });

}

ProductType.prototype.find = (id, callback) =>{
    createConnection();

    con.query("SELECT * FROM produtos_tipos WHERE id = ?", id, (err, rows) =>{
        if(err) callback({ error: true, msg: err});

        callback(rows);
    });
}

ProductType.prototype.create = (data ,callback) =>{
    createConnection();

    con.query("INSERT INTO produtos_tipos SET ?", data, (err, result) =>{
        if(err) return callback({ error: true, msg: err });

        callback({ created: true });
    });
}

ProductType.prototype.update = (data, id, callback) =>{
    createConnection();
    con.query("UPDATE produtos_tipos SET ? WHERE id = ?", [data, id], (err, result) =>{
        if(err) callback({ error: true, msg: err});

        callback(result);
    });
};

ProductType.prototype.delete = (id, callback) =>{
    createConnection();

    con.query("DELETE FROM produtos_tipos WHERE id = ?", id, (err, result) =>{
        if(err) callback({ error: true, msg: err});

        callback(result);
    });
};

module.exports = ProductType;