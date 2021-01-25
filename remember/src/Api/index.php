<?php
require '../../vendor/autoload.php';

use Api\Classes\Controllers\Middleware;

if(isset($_REQUEST)){
    
    $url = $_REQUEST['url'];
    $requestType = $_SERVER['REQUEST_METHOD'];

    $mid = new Middleware();
    $mid->treatUrl($url);

    if($mid->isValidUrl($url)){

        if($mid->isValidMethod($requestType)){
            
            $data = file_get_contents("php://input");
            $dataArray = json_decode($data, true);
            
            echo json_encode(
                $mid->doAction($requestType, $dataArray)
            );

        }else{
            $aMsg['status'] = 405;
            $aMsg['msg'] = "Método Inexistente";
            
            echo json_encode($aMsg);
        }   

    }else{
        $aMsg['status'] = 404;
        $aMsg['msg'] = "Url invalida";

        echo json_encode($aMsg);
    }
}