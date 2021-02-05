<?php

namespace Api\Classes\Daos;

use Api\Classes\Models\DB;

abstract class Dao extends DB{

    protected $sql;

    public abstract function findAll();
    public abstract function find($id);
    public abstract function save($obj);
    public abstract function update($obj, $colunas);
    public abstract function delete($id);

    public function createBaseSQL($type, $table, $columns = []){

        if($type == 'select'){
            $this->sql = "SELECT * FROM ${table}";

        }else if($type == 'insert'){
            $this->sql = "INSERT INTO ${table}";

            $this->sql .= "(";
            $i = 0;
            foreach ($columns as $column) {
                if($i != (count($columns) - 1 )){
                    $this->sql .= "$column,";
                }else{
                    $this->sql .= "$column";
                }
                $i++;
            }
            $this->sql .= ")";
        
        }else if($type == 'update'){
            $this->sql = "UPDATE ${table} SET ";

            $i = 0;
            foreach ($columns as $column) {
                if($i != (count($columns) - 1 )){
                    $this->sql .= "$column = :$column,";
                }else{
                    $this->sql .= "$column = :$column";
                }
                $i++;
            }
        
        }else if($type == 'delete'){
            $this->sql = "DELETE FROM ${table}";
        }

        return $this;
    }

    public function where(){
        $this->sql .= " WHERE ";
        return $this;
    }
    
    public function sqlAnd(){
        $this->sql .= " AND ";
        return $this;
    }

    public function equals($column){
        $this->sql .= " $column = :$column ";
    }

}