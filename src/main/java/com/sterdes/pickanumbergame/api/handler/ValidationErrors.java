package com.sterdes.pickanumbergame.api.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrors {

  private List<ValidationError> errors;
}
