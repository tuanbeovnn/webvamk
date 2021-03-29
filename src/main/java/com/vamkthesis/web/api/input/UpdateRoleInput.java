package com.vamkthesis.web.api.input;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleInput extends AbtractInput {
    private List<Long> idRoles;
}
