const routes = require('express').Router();
const Product = require("./../modulos/Product");

routes.get('/', (req, res) =>{
    let product = new Product;
    product.list(result =>{

        if(result.error) 
            res.status(400).send({ msg: "Ocorreu um erro ao buscar os produtos." });

        if(result.length > 0){
            res.send(result);
        }else{
            res.send({ msg: "Nenhum produto foi cadastrado" })
        }
    });
});

routes.get('/:id', (req, res) =>{

    let idProduto = req.params.id;

    if(isNaN(idProduto) || idProduto < 0){
        res.send({ msg: "Parametro de rota invalido."})

    }else{
        let product = new Product;
        product.find(idProduto, result =>{
            if(result.error) 
                res.status(400).send({ msg: "Ocorreu um erro ao buscar o produto." });

                if(result.length > 0){
                    res.send(result);
                }else{
                    res.send({ msg: "Produto não encontrado" })
                }
        });
    }
});

routes.post('/', (req, res) =>{

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

            let product = new Product;
            product.create(produto, result =>{

                if(result.error){
                    res.send({ msg : "Ocorreu um erro ao cadastrar um novo produto."});
                    return;
                }

                res.status(201).send({ msg: "Produto cadastrado com sucesso." });
            });
        }else{
            res.status(400).send({ msg: "Formato de dados inválido."})
        }
    }

});

routes.put('/:id', (req, res) =>{

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
                let product = new Product;

                product.find(idProduto, result =>{
                    if(result.error){
                        res.send({ msg : "Ocorreu um erro ao editar o produto."});
                        return;
                    }

                    if(result.length > 0){

                        product.update(produto, idProduto, result =>{
                            if(result.error){
                                res.send({ msg : "Ocorreu um erro ao editar o produto."});
                                return;
                        }

                            res.send({ msg: "Produto atualizado com sucesso." })
                        })
                    }else{
                        res.send({ msg: "Produto não encontrado." })
                    }
                });
            }else{
                res.status(400).send({ msg: "Formato de dados inválidos." });
            }
        }

    }
});

routes.delete('/:id', (req, res) =>{

    let idProduto = req.params.id;

    if(isNaN(idProduto) || idProduto < 0){
        res.send({ msg: "Parametro de rota inválido" });
    }else{

        let product = new Product;
        product.find(idProduto, result =>{

            if(result.error){
                res.send({ msg: "Ocorreu um erro ao deletar o produto." });
                return;
            }

            if(result.length > 0){

                product.delete(idProduto, result =>{
                    if(result.error)
                        res.send({ msg: "Ocorreu um erro ao deletar o produto." });
                    
                    res.send({ msg: "Produto deletado com sucesso." })
                });

            }else{
                res.send({ msg: "Produto não encontrado." });
            }
        });
    }

});

module.exports = routes;