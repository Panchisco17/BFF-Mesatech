package cl.duoc.bff_mesatech.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudProxyController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String msPedidosUrl = "http://localhost:8081"; // URL de tu microservicio de pedidos

    // Ruta proxy para la versión v1
    @PutMapping("/v1/{id}/estado")
    public ResponseEntity<String> actualizarEstadoV1Proxy(@PathVariable Long id, @RequestBody String estado) {
        String url = msPedidosUrl + "/v1/solicitudes/" + id + "/estado";
        HttpEntity<String> requestEntity = new HttpEntity<>(estado);
        // Reenviamos la petición al microservicio de pedidos
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    // Ruta proxy para crear una solicitud
    @PostMapping("/v1")
    public ResponseEntity<String> crearSolicitudProxy(@RequestBody String solicitudJson) {
        String url = msPedidosUrl + "/v1/solicitudes";

        // Configuramos explícitamente que estamos enviando JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(solicitudJson, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }
}