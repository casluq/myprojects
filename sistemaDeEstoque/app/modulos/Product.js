const mysql = require('mysql');
var con = null;

function createConnection(){
    con = mysql.createConnection({
        host: "localhost",
        user: "root",
        password: "",
        database: "estoque"
    });    
}

function Product(name, code, value, estoque_minimo, qtd_em_estoque, id_tipo){
    this.name = name;
    this.code = code;
    this.value = value;
    this.estoque_minimo = estoque_minimo;
    this.qtd_em_estoque = qtd_em_estoque;
    this.id_tipo = id_tipo;
}


Product.prototype.list = callback =>{
    createConnection();

    con.query("SELECT * FROM produtos", (err, rows) =>{
        if(err) callback({ error: true, msg: err});

        callback(rows);
    });
}

Product.prototype.find = (id, callback) =>{
    createConnection();

    con.query("SELECT * FROM produtos WHERE id = ?", id, (err, rows) =>{
        if(err) callback({ error: true, msg: err});

        callback(rows);
    });
}

Product.prototype.create = (data, callback) =>{
    createConnection();

    con.query("INSERT INTO produtos SET ?", data, (err, result) =>{
        if(err) callback({ error: true, msg: err});

        callback({ created: true });
    });
}

Product.prototype.update = (data, id, callback) =>{
    createConnection();

    con.query("UPDATE produtos SET ? WHERE id = ?", [data, id], (err, result) =>{
        if(err) callback({ error: true, msg: err});

        callback({ created: true });
    });
}

Product.prototype.delete = (id, callback) =>{
    createConnection();

    con.query("DELETE FROM produtos WHERE id = ?", id, (err, result) =>{
        if(err) callback({ error: true, msg: err});

        callback(result);
    })
}

module.exports = Product;