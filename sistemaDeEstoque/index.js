const express = require('express');
const mysql = require('mysql');

const port = 3000;
const app = express();
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

const ProductType = require("./app/modulos/ProductType");
const Product = require("./app/modulos/Product");

var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "",
    database: "estoque"
})

//Tipos de Produto
app.get('/produtos/tipos', (req, res) =>{
    let productType = new ProductType();

    productType.list(result =>{
        if(result.error)
            res.status(400).send({ msg: "Ocorreu um erro ao listar os produtos." });
        
        res.send(result);
    });
});

app.get('/produtos/tipos/:id', (req, res) =>{

    let idTipo = req.params.id;

    if(isNaN(idTipo) || idTipo < 0){
        res.send({ msg: "Parametro de rota invalido."})
    }else{
        let productType = new ProductType();

        productType.find(idTipo, result =>{
            if(result.error)
                res.status(400).send({ msg: "Ocorreu um erro ao listar os produtos." });
        
            if(result.length > 0){
                res.send(result);
            }else{
                res.send({ msg: "Produto não encontrado" })
            }
       });
    }
});

app.post('/produtos/tipos', (req, res) =>{

    if(Object.entries(req.body).length == 0){
        
        res.status(400).send({ 
            message: "Falha ao criar produto. Nenhum dado foi enviado."
        });

    }else{
        //TODO validar dados da requisição
        let tipoProduto = req.body.tipo;

        if(tipoProduto){
            if(!tipoProduto.name){
                res.status(400).send({ msg: 'O atribudo name não foi especificado.' });
            }else{

                let productType = new ProductType();
                
                productType.create(tipoProduto, (result)=>{
                    if(result.error)  
                        res.status(400).send({ msg: "Ocorreu um erro ao cadastrar o tipo de produto." });

                    res.status(201).send({ msg: "Cadastro realizado com sucesso!" });
                });                  
            }   
        }else{
            res.status(400).send({ msg: "Formato de dados inválido."});
        }
        
    }
});

app.put('/produtos/tipos/:id', (req, res) =>{

    let idTipo = req.params.id;

    if(isNaN(idTipo) || idTipo < 0){
        res.send({ msg: "Parametro de rota invalido."})
    }else{

        if(Object.entries(req.body).length == 0){
        
            res.status(400).send({
                message: "Falha ao atualizar tipo de produto. Nenhum dado foi enviado."
            });
    
        }else{

            let tipoProduto = req.body.tipo;

            if(tipoProduto){

                if(!tipoProduto.name){
                    res.send({ msg: "O campo nome não foi informado." })
                    return;
                }

                let productType = new ProductType;
                productType.find(idTipo, result =>{

                    if(result.length > 0){

                        productType.update(tipoProduto, idTipo, result =>{
                            if(result.error)
                                res.send({ msg: "Falha ao atualizar o Tipo de Produto" })

                            res.send({ msg: "Tipo de produto atualizado com sucesso." })
                        });
                    }else{
                        res.send({ msg: "Falha ao atualizar. Registro não encontrado" });
                    }
                });  

            }else{
                res.status(400).send({ msg: "Formato de dados inválido."})
            }

        }
    }

});

app.delete('/produtos/tipos/:id', (req, res) =>{

    let idTipo = req.params.id;

    if(isNaN(idTipo) || idTipo < 0){
        res.send({ msg: "Parametro de rota invalido."})
    }else{

        let productType = new ProductType;
        productType.find(idTipo, result =>{

            if(result.length > 0){

                productType.delete(idTipo, result =>{
                    if(result.error)
                        res.send({ msg: "Falha ao deletar registro." })

                    res.send({ msg: "Tipo de produto deletado com sucesso." })

                });

            }else{
                res.send({ msg: "Registro não encontrado" })
            }
        });
    }

});

//PRODUTOS


app.get('/produtos', (req, res) =>{

    con.query("SELECT * FROM produtos", (err, rows) =>{

        res.send(rows);
    });

});

app.get('/produtos/:id', (req, res) =>{

    let idProduto = req.params.id;

    if(isNaN(idProduto) || idProduto < 0){
        res.send({ msg: "Parametro de rota invalido."})

    }else{
        con.query("SELECT * FROM produtos WHERE id  = ?", idProduto, (err, rows) =>{
            if(err){
                res.send({ 
                    msg: "Ocorreu um erro ao buscar os dados", 
                    err: err 
                });
                return;
            }

            if(rows.length > 0){
                res.send(rows);
            }else{
                res.send({ msg: "Produto não encontrado" })
            }

        });

    }

});

app.post('/produtos', (req, res) =>{

    if(Object.entries(req.body).length == 0){
        
        res.status(400).send({
            message: "Falha ao criar produto. Nenhum dado foi enviado."
        });

    }else{
        let produto = req.body.produto;

        if(produto){

            if(!produto.name){
                res.status(400).send({ msg: "Nome do produto não informado." })
            }

            if(!produto.code){
                res.status(400).send({ msg: "Código do produto não informado." })
            }

            if(!produto.value){
                res.status(400).send({ msg: "Valor do produto não informado." })
            }

            if(!produto.estoque_minimo){
                res.status(400).send({ msg: "Estoque mínimo do produto não informado." })
            }

            if(!produto.qtd_em_estoque){
                res.status(400).send({ msg: "Quandidade em estoque do produto não informado." })
            }

            if(!produto.id_tipo){
                res.status(400).send({ msg: "Tipo de produto não informado." })
            }

            con.query("INSERT INTO produtos SET ?", produto, (err, result) =>{
                if(err) 
                    res.send({ msg : "Ocorreu um erro ao cadastrar um novo produto."})

                res.status(201).send({ msg: "Produto cadastrado com sucesso." });
            });

        }else{
            res.status(400).send({ msg: "Formato de dados inválido."})
        }
    }

});

app.put('/produtos/:id', (req, res) =>{

    let id = req.params.id;

    //verifica se o parametro passado via querystring
    //não é um numero ou se é um numero negativo
    if (isNaN(id) || id < 0){
        res.send({ msg: "Parametro de rota inválido" });
    }else{

        //verifica se o corpo da requisição está vazio
        if(Object.entries(req.body).length == 0){
            res.send({ msg: "Falha ao atualizar produto. Nenhum dado foi enviado" })
            return;
        }else{

            let produto = req.body.produto;

            //verifica se existe um objeto produto no corpo da requisição
            if(produto){
                // caso sim -> faz as validações dos atributos do objeto

                if(!produto.name){
                    res.status(400).send({ msg: "Nome do produto não informado." })
                    return;
                }
    
                if(!produto.code){
                    res.status(400).send({ msg: "Código do produto não informado." })
                    return;
                }
    
                if(!produto.value){
                    res.status(400).send({ msg: "Valor do produto não informado." })
                    return;
                }
    
                if(!produto.estoque_minimo){
                    res.status(400).send({ msg: "Estoque mínimo do produto não informado." })
                    return;
                }
    
                if(!produto.qtd_em_estoque){
                    res.status(400).send({ msg: "Quandidade em estoque do produto não informado." })
                    return;
                }
    
                if(!produto.id_tipo){
                    res.status(400).send({ msg: "Tipo de produto não informado." })
                    return;
                }

                let idProduto = req.params.id;
            
                con.query("SELECT * FROM produtos WHERE id = ?", idProduto, (err, rows) =>{

                    if(rows.length == 0){
                        res.send({ msg: "Registro não encontrado." })
                        return;
                    }

                    con.query("UPDATE produtos SET ? WHERE id = ?", [produto, idProduto], (err, result) =>{
                        if(err){
                            res.send({ msg : "Ocorreu um erro ao cadastrar um novo produto."})
                            return;
                        }
    
                        res.send({ msg: "Produto atualizado com sucesso." });
                    })
                })

            }else{
                res.status(400).send({ msg: "Formato de dados inválidos." });
            }
        }

    }
});

app.delete('/produtos/:id', (req, res) =>{

    let idProduto = req.params.id;

    if(isNaN(idProduto) || idProduto < 0){
        res.send({ msg: "Parametro de rota inválido" });
    }else{

        con.query("SELECT * FROM produtos WHERE id = ?", idProduto, (err, rows) =>{
            if(err)
                res.send({
                    msg: "Ocorreu um erro ao deletar o produto.",
                    err: err
                })

            if(rows.length > 0){

                con.query("DELETE FROM produtos WHERE id = ?", idProduto, (err, result) =>{
                    
                    res.send({ msg: "Produto deletado com sucesso." })

                })

            }else{
                res.send({ msg: "Registro não encontrado." });
            }
        }) 
    }

});

app.listen(port, () =>{
});