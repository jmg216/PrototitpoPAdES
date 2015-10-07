/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.entityWS;

import java.util.List;
import py.gov.hacienda.digital.doc.CampoTipoValor;

/**
 *
 * @author JMiraballes
 */
public class RequestPdfWS {
    
    public static String ANIO = "anio";
    public static String CEDULA = "cedula";    
    public static String SOLNUMERO = "solNumero";
    public static String TIPO_DOCUMENTO = "tipoDocumento";   
    
    private String cedula;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    private List<CampoTipoValor> parametros;
    private List<CampoTipoValor> parametrosExtra;

    public List<CampoTipoValor> getParametros() {
        return parametros;
    }

    public void setParametros(List<CampoTipoValor> parametros) {
        this.parametros = parametros;
    }

    public List<CampoTipoValor> getParametrosExtra() {
        return parametrosExtra;
    }

    public void setParametrosExtra(List<CampoTipoValor> parametrosExtra) {
        this.parametrosExtra = parametrosExtra;
    }


    public String getParametroExtraValue( String name ){
        if (parametrosExtra != null){
            for (CampoTipoValor param : parametrosExtra){
                if (param.getCampo().equals(name)){
                    return param.getValor();
                }
            }
        }
        return null;
    }
    
    public String getParametroValue( String name ){
        if (parametros != null){
            for (CampoTipoValor param : parametros){
                if (param.getCampo().equals(name)){
                    return param.getValor();
                }
            }
        }
        return null;
    }    
    
    
}
