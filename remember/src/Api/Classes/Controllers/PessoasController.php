<?php

namespace Api\Classes\Controllers;

use Api\Classes\Models\Pessoa;
use Api\Classes\Daos\PessoasDAO;
use Exception;

class PessoasController
{

    function index()
    {
        try {
            $pessoaDao = new PessoasDAO();
            return $pessoaDao->findAll();
        } catch (Exception $e) {
            $array = array();
            $array['status'] = "400";
            $array['msg'] = "Ocorreu um Erro: " . $e->getMessage();

            return $array;
        }
    }

    function find($id)
    {
        try {
            $pessoaDao = new PessoasDAO();
            return $pessoaDao->find($id);
        } catch (Exception $e) {
            $array = array();
            $array['status'] = "400";
            $array['msg'] = "Ocorreu um Erro: " . $e->getMessage();

            return $array;
        }
    }

    function create($data)
    {
        $pessoa = new Pessoa();

        if ($pessoa->validData($data)) {
            $pessoa->make($data);

            try {
                $pessoaDao = new PessoasDAO();
                return $pessoaDao->save($pessoa);
            } catch (Exception $e) {
                $array = array();
                $array['status'] = "400";
                $array['msg'] = "Ocorreu um Erro: " . $e->getMessage();

                return $array;
            }
        } else {
            $aErro['status'] = "400";
            $aErro['msg'] = "Formato de dados inválido. Verifique corretamente os campos que estão sendo enviados.";

            return $aErro;
        }
    }

    function update($data, $id)
    {
        $pessoa = new Pessoa();

        if ($pessoa->validData($data)) {
            $pessoa->make($data);
            $pessoa->setId($id);

            try {
                $pessoaDao = new PessoasDAO();
                return $pessoaDao->update($pessoa, $id);
            } catch (Exception $e) {
                throw $e;
            }
        } else {
            $aErro['status'] = "400";
            $aErro['msg'] = "Formato de dados inválido. Verifique corretamente os campos que estão sendo enviados.";

            return $aErro;
        }
    }

    function delete($id)
    {
        try {
            $pessoaDao = new PessoasDAO();
            return $pessoaDao->delete($id);
        } catch (Exception $e) {
            throw $e;
        }
    }
}
