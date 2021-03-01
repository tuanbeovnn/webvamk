package com.vamkthesis.web.api.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbtractInput {

    protected Long id;
}
