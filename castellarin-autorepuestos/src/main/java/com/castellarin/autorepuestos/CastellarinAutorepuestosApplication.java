package com.castellarin.autorepuestos;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class CastellarinAutorepuestosApplication {

    /*TODO
    *  IMPLEMENTAR LOGICA DE COMPRA (MERCADO PAGO)
    * IMPLEMENTAR LOGICA DE CALCULO DE ENVIO
    * IMPLEMENTAR LOGICA DE INICIO DE SESION
    * */

	@Value("${mp.test.access-token}")
	private String accessToken;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(CastellarinAutorepuestosApplication.class, args);
	}

	@jakarta.annotation.PostConstruct
	public void initMP() {
		MercadoPagoConfig.setAccessToken(accessToken);
	}
}
