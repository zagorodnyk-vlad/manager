package com.man.ger.manager.service;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.man.ger.manager.dto.CurrencyDto;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@Service
public class CurrencyConnectionService {

    public List<CurrencyDto> getCurrency() throws IOException {
        Request request = Request.Get("https://api.monobank.ua/bank/currency");
        HttpEntity resp = request.execute().returnResponse().getEntity();
        //разбираем ответ
        BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getContent()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyDto> curencyDtoList = objectMapper.readValue(response.toString(), new TypeReference<List<CurrencyDto>>() {
        });

        return curencyDtoList;
    }
}
