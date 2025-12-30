package com.exampledigisphere.vitapetcare.config.root.mapper;

public interface Mapper<I, D, O> {
  D toDomain(I input);

  O toOutput(D domain);
}
