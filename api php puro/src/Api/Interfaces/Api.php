<?php

namespace Api\Interfaces;

interface Api{

    /**
     * Validates the url by looking for a corresponding controller
     */
    public function isValidUrl();

    /**
     * Validates the method by looking for a corresponding method
     */
    public function isValidMethod($requestType);

    /**
     * Xcute method
     */
    public function doAction($request, $data);

    /**
     * Clears the parameters of anything malicious
     * 
     * return $clearedParams: String
     */
    public function clearParams($params);

}