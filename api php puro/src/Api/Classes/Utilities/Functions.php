<?php

namespace Api\Classes\Utilities;

class Functions{

    public static function pdoDebugger($sql, $aCampos){

        foreach($aCampos as $key => $value){
            $sql = str_replace($key, $value, $sql);
        }

        return $sql;
    }
}