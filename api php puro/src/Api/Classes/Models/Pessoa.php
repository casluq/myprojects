<?php

namespace Api\Classes\Models;

use Api\Classes\Models\Model;

class Pessoa extends Model
{

    private $id;
    private $name;
    private $age;
    private $cpf;
    private $phone;

    public function make($data)
    {
        $aPessoa = $data['pessoa'];

        if(key_exists("id",$aPessoa)){
            $this->id = $aPessoa['id'];
        }
        
        $this->name = trim($aPessoa['name']);
        $this->age = $aPessoa['age'];
        $this->cpf = $aPessoa['cpf'];
        $this->phone = $aPessoa['phone'];
    }

    public function validData($data)
    {

        if ($data == null) {
            return false;
        }

        if (!is_array($data)) {
            return false;
        }

        if (count($data) <= 0) {
            return false;
        }

        if (key_exists("pessoa", $data)) {
            $aPessoa = $data['pessoa'];

            if (!key_exists("name", $aPessoa)) {
                return false;
            }

            if (!key_exists("age", $aPessoa)) {
                return false;
            }

            if (!key_exists("cpf", $aPessoa)) {
                return false;
            }

            if (!key_exists("phone", $aPessoa)) {
                return false;
            }

            if (!preg_match("/\\w{2,}/", $aPessoa['name'])) {
                return false;
            }

            if (!preg_match("/\\d{1,3}/", $aPessoa['age'])) {
                return false;
            }

            if (!preg_match("/\\d{11}/", $aPessoa['cpf'])) {
                return false;
            }

            if (!preg_match("/\\d{8,9}/", $aPessoa['phone'])) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public function getId()
    {
        return $this->id;
    }

    public function setId($id)
    {
        $this->id = $id;
    }

    public function getName()
    {
        return $this->name;
    }

    public function setName($name)
    {
        $this->name = $name;
    }

    public function getAge()
    {
        return $this->age;
    }

    public function setAge($age)
    {
        $this->age = $age;
    }

    public function getCpf()
    {
        return $this->cpf;
    }

    public function setCpf($cpf)
    {
        $this->cpf = $cpf;
    }

    public function getPhone()
    {
        return $this->phone;
    }

    public function setPhone($phone)
    {
        $this->phone = $phone;
    }
}
