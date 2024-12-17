package br.com.nicoletti.loto.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.nicoletti.loto.exceptions.RestServiceException;

@Service
public class RestService {

    private final Logger logger = LoggerFactory.getLogger(RestService.class);

    public static HttpHeaders defaulHeadersToLotteries() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, "security=true");
        return httpHeaders;
    }

    public String doSimpleGet(String url, HttpHeaders httpHeaders) {
        HttpEntity<HttpHeaders> header = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, header, String.class);

        HttpStatus statusCode = exchange.getStatusCode();
        if (!HttpStatus.OK.equals(statusCode)) {
            throw new RestServiceException("Erro na requisição, url: " + url + " header: " + header);
        }

        return exchange.getBody();
    }

    public String doSimpleGet(String url) {
        return this.doSimpleGet(url, new HttpHeaders());
    }

    @Deprecated
    public String doSimpleGetToLotteries(String url) {
        return this.doSimpleGet(url, defaulHeadersToLotteries());
    }

    public String doGet(String server, String path, Map<String, Object> parameters, HttpHeaders httpHeaders) {
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        HttpEntity<HttpHeaders> header = new HttpEntity<>(httpHeaders);

        String url = urlWithQueryParams(server, path, parameters);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, header, String.class);

        HttpStatus statusCode = exchange.getStatusCode();
        if (!HttpStatus.OK.equals(statusCode)) {
            throw new RestServiceException("Erro na requisição, url: " + url + " header: " + header);
        }

        return exchange.getBody();
    }

    public String doGet(String server, String path, Map<String, Object> parameters) {
        return doGet(server, path, parameters, new HttpHeaders());
    }

    private String urlWithQueryParams(String server, String path, Map<String, Object> parameters) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(server.concat(path));
        parameters.forEach((k, v) -> {
            builder.queryParam(k, v);
        });
        return builder.toUriString();
    }

}
