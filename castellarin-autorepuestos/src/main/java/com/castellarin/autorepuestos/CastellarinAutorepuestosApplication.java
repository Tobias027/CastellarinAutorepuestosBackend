package com.castellarin.autorepuestos;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("{$mp.test.access-token}")
	private static String accessToken;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		MercadoPagoConfig.setAccessToken(accessToken);
		SpringApplication.run(CastellarinAutorepuestosApplication.class, args);
	}

}
