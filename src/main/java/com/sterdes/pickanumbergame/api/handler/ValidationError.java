package com.sterdes.pickanumbergame.api.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {

  private String code;
  private List<String> arguments;
}
