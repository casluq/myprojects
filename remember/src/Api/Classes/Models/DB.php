<?php

namespace Api\Classes\Models;

use Api\Interfaces\DBConnection;
use PDO;

class DB implements DBConnection{

    protected $con;

    public function startConnection()
    {
        $this->con = new PDO("mysql:host=localhost;dbname=remember2021", "root", "");
    }
}