<?php

namespace Api\Classes\Daos;  

use Api\Classes\Daos\Dao;
use Api\Classes\Utilities\Functions;
use PDO;

class PessoasDAO extends Dao{

    private $columnsOfTable;

    function __construct(){
        $this->startConnection();

        $this->columnsOfTable = array();

        array_push($this->columnsOfTable,"name");
        array_push($this->columnsOfTable,"age");
        array_push($this->columnsOfTable,"cpf");
        array_push($this->columnsOfTable,"phone");
    }

    public function find($id){
        $this->createBaseSQL("select", "pessoas")
            ->where()
            ->equals("id");

        $stmt = $this->con->prepare($this->sql);
        
        $aCampos[":id"] =  $id;

        // echo '<pre>';
        // print_r(Functions::pdoDebugger($this->sql, $aCampos));
        // die;
        
        $stmt->execute($aCampos);

        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $result;
    }

    public function findAll(){
        $this->createBaseSQL("select", "pessoas");

        $stmt = $this->con->prepare($this->sql);
        $stmt->execute();

        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $result;
    }  

    public function save($pessoa){
       $this->createBaseSQL("insert", "pessoas", $this->columnsOfTable);
       $this->sql .= 'VALUES(:name, :age, :cpf, :phone)';

        $stmt = $this->con->prepare($this->sql);

        //Array de campos utilizado para o bind ao executar o sql
        $aCampos[":name"] =  $pessoa->getName();
        $aCampos[":age"] =  $pessoa->getAge();
        $aCampos[":cpf"] =  $pessoa->getCpf();
        $aCampos[":phone"] =  $pessoa->getPhone();

        // echo '<pre>';
        // print_r(Functions::pdoDebugger($this->sql, $aCampos));
        // die;

        $stmt->execute($aCampos);
        
    }

    public function update($pessoa, $colunas){
        $this->createBaseSQL("update", "pessoas", $this->columnsOfTable)
            ->where()
            ->equals("id");

        $stmt = $this->con->prepare($this->sql);

        //Array de campos utilizado para o bind ao executar o sql
        $aCampos[":id"] =  $pessoa->getId();
        $aCampos[":name"] =  $pessoa->getName();
        $aCampos[":age"] =  $pessoa->getAge();
        $aCampos[":cpf"] =  $pessoa->getCpf();
        $aCampos[":phone"] =  $pessoa->getPhone();

        // echo '<pre>';
        // print_r(Functions::pdoDebugger($this->sql, $aCampos));
        // die;

        return $stmt->execute();
    }

    public function delete($id){
        $this->createBaseSQL("delete", "pessoas")
            ->where()
            ->equals("id");

        $stmt = $this->con->prepare($this->sql);
        $aCampos[":id"] =  $id;

        // echo '<pre>';
        // print_r(Functions::pdoDebugger($this->sql, $aCampos));
        // die;

        return $stmt->execute($aCampos);
    }
   
}