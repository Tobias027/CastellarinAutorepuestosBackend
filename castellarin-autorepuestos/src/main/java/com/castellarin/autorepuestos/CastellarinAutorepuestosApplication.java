package com.castellarin.autorepuestos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class CastellarinAutorepuestosApplication {

    /*TODO
    *  IMPLEMENTAR LOGICA DE COMPRA (MERCADO PAGO)
    * IMPLEMENTAR LOGICA DE CALCULO DE ENVIO
    * IMPLEMENTAR LOGICA DE INICIO DE SESION
    * */

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(CastellarinAutorepuestosApplication.class, args);
	}

}
