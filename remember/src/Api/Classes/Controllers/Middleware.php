<?php

namespace Api\Classes\Controllers;

use Api\Interfaces\Api;

class Middleware implements Api{

    private $controller;
    private $metodo;
    private $params;

    function __constructor(){

    }

    public function treatUrl($url){
        $segmentosDaRota =  explode('/', $url);
        $this->controller = "Api\Classes\Controllers\\" . ucfirst($segmentosDaRota[0]) . "Controller";
        $this->params = count($segmentosDaRota) > 1 ? $segmentosDaRota[1] : '';

        $this->clearParams($this->params);
    }

    public function isValidUrl()
    {
        return class_exists($this->controller);
    }

    public function isValidMethod($requestType)
    {
        if($requestType == "GET"){
            $this->metodo = "index";

            if($this->params){
                $this->metodo = "find";
            }
            
        }else if($requestType == "POST"){

            if($this->params) return false;

            $this->metodo = "create";
            
        }else if($requestType == "PUT"){
            $this->metodo = "update";
            
        }else if($requestType == "DELETE"){
            $this->metodo = "delete";
        }
        
        return method_exists($this->controller, $this->metodo);
    }

    public function doAction($requestType, $data){
        $metodo = $this->metodo;
        $obj = new $this->controller;

        if($requestType != "GET" && $requestType != "DELETE"){
            return $obj->$metodo($data, $this->params);
        }else{
            return $obj->$metodo($this->params);
        }
    }

    public function clearParams($urlParams){
        $this->params = trim($urlParams);
        $this->params = strip_tags($urlParams);
    }
}