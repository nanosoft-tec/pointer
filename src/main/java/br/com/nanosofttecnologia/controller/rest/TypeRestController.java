package br.com.nanosofttecnologia.controller.rest;

import br.com.nanosofttecnologia.model.TypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TypeRestController {

  @GetMapping(value = "/secure/type/list", produces = APPLICATION_JSON_VALUE)
  public List<String> list() {
    return Arrays.asList(TypeEnum.ENTRADA.toString(), TypeEnum.SAIDA.toString());
  }
}
