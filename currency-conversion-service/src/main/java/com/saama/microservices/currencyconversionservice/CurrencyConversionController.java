package com.saama.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity =
                new RestTemplate().<CurrencyConversionBean>getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);

        CurrencyConversionBean responseEntityBody = responseEntity.getBody();
        return new CurrencyConversionBean(responseEntityBody.getId(), from, to,
                responseEntityBody.getConversionMultiple(), quantity, quantity.multiply(responseEntityBody.getConversionMultiple()), responseEntityBody.getPort());
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversionBean responseEntityBody = proxy.retrieveExchangeValue(from, to);
        return new CurrencyConversionBean(responseEntityBody.getId(), from, to,
                responseEntityBody.getConversionMultiple(), quantity, quantity.multiply(responseEntityBody.getConversionMultiple()), responseEntityBody.getPort());
    }

}
